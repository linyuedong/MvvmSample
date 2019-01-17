package com.pax.mvvmsample.ui.gank.beauty;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentBeautyBinding;
import com.pax.mvvmsample.BR;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding,BeautyViewModel> {

    private SmartRefreshLayout refreshLayout;

    public BeautyFragment() {

    }

    public static BeautyFragment newInstance(){
        return new BeautyFragment();
    }

    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void initViewAndEvent() {
        initView();
        initEvent();
        mViewModel.loadBeautyData();
    }

    private void initEvent() {
        mViewModel.status.observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if (throwable != null) {
                    Snackbar.make(mRootView, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mViewModel.loadMoreState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean successs) {
                refreshLayout.finishLoadMore(successs);
            }
        });

        mViewModel.refreshState.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean success) {
                refreshLayout.finishRefresh(success);
            }
        });
    }

    private void initView() {
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        refreshLayout = mBinding.refreshLayout;
        setRefreshLsyout(refreshLayout);
    }


}
