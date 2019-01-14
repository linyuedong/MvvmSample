package com.pax.mvvmsample.ui.gank.android;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.library.Utils.LogUtlis;
import com.example.library.base.BaseFragment;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.example.library.view.webView.WebViewActivity;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentAndroidBinding;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.databinding.WebActivity;
import com.pax.mvvmsample.ui.wanandroid.WanAndroidFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class AndroidFragment extends BaseFragment<FragmentAndroidBinding, AndroidViewModel> {

    private static final String TYPE = "type";
    private SmartRefreshLayout refreshLayout;

    public String mType;
    public AndroidFragment() {
    }

    public static AndroidFragment newInstance(String type) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
        LogUtlis.i("mType = " + mType);
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
        mViewModel.setType(mType);
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
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
               WebViewActivity.loadUrl(getActivity(),item.bean.getUrl(),item.bean.getDesc());
           }


       });

    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        mViewModel.loadAndroidData();
    }
}
