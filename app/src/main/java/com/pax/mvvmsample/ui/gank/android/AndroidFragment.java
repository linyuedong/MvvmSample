package com.pax.mvvmsample.ui.gank.android;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import com.example.library.base.BaseFragment;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentAndroidBinding;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.databinding.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class AndroidFragment extends BaseFragment<FragmentAndroidBinding, AndroidViewModel> {


    private SmartRefreshLayout refreshLayout;

    public AndroidFragment() {
    }

    public static AndroidFragment newInstance() {
        return new AndroidFragment();
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initViewAndEvent() {
        initView();
        initEvent();
        mViewModel.loadAndroidData();
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
        refreshLayout = mBinding.refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        mViewModel.adapter.setOnItemClickListener(new BaseRecycleViewAdapter.OnItemClickListener<AndroidItemViewModel>() {
           @Override
           public void onClick(View v, int position, AndroidItemViewModel item) {
               Intent intent = new Intent();
               intent.setClass(v.getContext(), WebActivity.class);
               intent.putExtra("desc",item.bean.getDesc());
               intent.putExtra("url",item.bean.getUrl());
               startActivity(intent);
           }


       });

    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mViewModel.loadAndroidData();
    }
}
