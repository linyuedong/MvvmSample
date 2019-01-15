package com.example.library.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.library.R;
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.lang.reflect.ParameterizedType;


public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {


    protected V mBinding;
    protected View mRootView;
    protected VM mViewModel;
    protected RelativeLayout mContainer;
    public boolean hasLoadData = false;
    public boolean isViewPrepared = false;
    private View mLoadingView;
    private LottieAnimationView mLoadingAnimation;
    private View mRefresh;
    private View mRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataBinding(inflater, container);
        initRootView(inflater);
        return mRootView;
    }

    private void initLoaddingView() {
        mLoadingView = ((ViewStub) getView(R.id.vs_loading)).inflate();
        mLoadingAnimation = mLoadingView.findViewById(R.id.loading_animation);
        mRefresh = getView(R.id.ll_error_refresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        mBinding.getRoot().setVisibility(View.GONE);
        showLoading();
    }

    private void initRootView(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.fragment_base, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(params);
        mContainer = mRootView.findViewById(R.id.container);
        mContainer.addView(mBinding.getRoot());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLoaddingView();
        isViewPrepared = true;
        lazyLoad();
    }


    protected abstract void initViewAndEvent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasLoadData = false;
        isViewPrepared = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.unbind();
        getLifecycle().removeObserver(mViewModel);
        mViewModel = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }

    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isViewPrepared && !hasLoadData) {
            hasLoadData = true;
            initViewAndEvent();
        }
    }

    private void initDataBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayoutId(), container, false);
        Class<VM> clazz = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
       // mViewModel = (VM) ViewModelProviders.of(getActivity()).get(clazz);
        mViewModel = (VM) ViewModelProviders.of(this).get(clazz);
        mBinding.setVariable(initBR(), mViewModel);
        getLifecycle().addObserver(mViewModel);
        mViewModel.addLifeCycle(this);
        registorUIStatusLiveDataCallBack();
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }


    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {
        if (mLoadingView != null && mLoadingView.getVisibility() != View.VISIBLE) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        playAnimation();
        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
    }

    public void playAnimation() {
        if (mLoadingAnimation != null) {
            mLoadingAnimation.setAnimation("loading_bus.json");
            mLoadingAnimation.loop(true);
            mLoadingAnimation.playAnimation();
        }
    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mLoadingView != null && mLoadingView.getVisibility() != View.GONE) {
            mLoadingView.setVisibility(View.GONE);
        }
        mLoadingAnimation.cancelAnimation();
        if (mRefresh.getVisibility() != View.GONE) {
            mRefresh.setVisibility(View.GONE);
        }
        if (mBinding.getRoot().getVisibility() != View.VISIBLE) {
            mBinding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLoadingView != null && mLoadingView.getVisibility() != View.GONE) {
            mLoadingView.setVisibility(View.GONE);
        }
        mLoadingAnimation.cancelAnimation();
        if (mRefresh.getVisibility() != View.VISIBLE) {
            mRefresh.setVisibility(View.VISIBLE);
        }
        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
    }



    private void registorUIStatusLiveDataCallBack(){
        mViewModel.mUIStatus.showContentLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                LogUtlis.i("receive : " + mViewModel.hashCode());
                showContentView();
            }
        });
        mViewModel.mUIStatus.showErrorLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                showError();
            }
        });

        mViewModel.mUIStatus.showLoaddingtLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                showLoading();

            }
        });


        mViewModel.mUIStatus.refreshState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Utils.checkNotNull(mRefreshLayout);
                if(mRefreshLayout instanceof SmartRefreshLayout){
                    ((SmartRefreshLayout)mRefreshLayout).finishRefresh(aBoolean);
                }
            }
        });

        mViewModel.mUIStatus.loadMoreState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Utils.checkNotNull(mRefreshLayout);
                if(mRefreshLayout instanceof SmartRefreshLayout){
                    ((SmartRefreshLayout)mRefreshLayout).finishLoadMore(aBoolean);
                }
            }
        });


        mViewModel.mUIStatus.status.observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if (throwable != null) {
                    Snackbar.make(mRootView, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    protected void setRefreshLsyout(View refreshLsyout){
        mRefreshLayout = refreshLsyout;
    }

    protected abstract int initBR();


    public abstract int getLayoutId();
}
