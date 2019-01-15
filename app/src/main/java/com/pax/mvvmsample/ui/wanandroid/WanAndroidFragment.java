package com.pax.mvvmsample.ui.wanandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.adapter.SimpleFragmentPagerAdapter;
import com.pax.mvvmsample.databinding.FragmentGankBinding;
import com.pax.mvvmsample.databinding.FragmentWanAndroidBinding;
import com.pax.mvvmsample.ui.gank.android.AndroidFragment;
import com.pax.mvvmsample.ui.gank.beauty.BeautyFragment;
import com.pax.mvvmsample.ui.home.HomeActivity;
import com.pax.mvvmsample.ui.wanandroid.home.HomeWanAnroidFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WanAndroidFragment extends BaseFragment<FragmentWanAndroidBinding,WanAndroidViewModel> {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public WanAndroidFragment() {
    }

    public static WanAndroidFragment newInstance(String param1) {
        WanAndroidFragment fragment = new WanAndroidFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected void initViewAndEvent() {
        showContentView();
        initActionBar();
        initTab();
    }

    private void initTab() {
        List<String> titles = Arrays.asList("首页", "知识体系", "导航", "其他");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeWanAnroidFragment.newInstance());
        fragmentList.add(AndroidFragment.newInstance("iOS"));
        fragmentList.add(AndroidFragment.newInstance("休息视频"));
        fragmentList.add(BeautyFragment.newInstance());
        SimpleFragmentPagerAdapter myAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titles);
        mBinding.vpWanAndroid.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mBinding.vpWanAndroid.setOffscreenPageLimit(0);
        myAdapter.notifyDataSetChanged();
        mBinding.tabWanAndroid.setTabMode(TabLayout.MODE_FIXED);
        mBinding.tabWanAndroid.setupWithViewPager(mBinding.vpWanAndroid);
    }

    private void initActionBar() {
        ((HomeActivity)getActivity()).setActionBarTitle("Gank");
    }

    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wan_android;
    }


}
