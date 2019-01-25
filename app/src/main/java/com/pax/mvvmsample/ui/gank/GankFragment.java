package com.pax.mvvmsample.ui.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.adapter.SimpleFragmentPagerAdapter;
import com.pax.mvvmsample.databinding.FragmentGankBinding;
import com.pax.mvvmsample.ui.gank.android.AndroidFragment;
import com.pax.mvvmsample.ui.gank.beauty.BeautyFragment;
import com.pax.mvvmsample.ui.home.HomeActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GankFragment extends BaseFragment<FragmentGankBinding, GankViewModel> {


    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;


    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initViewAndEvent() {
        initTabLayout();
    }

    private void initTabLayout() {
        showContentView();
        initTab();
    }

    private void initTab() {
        List<String> titles = Arrays.asList("安卓", "iOS", "休息视频", "福利");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(AndroidFragment.newInstance("Android"));
        fragmentList.add(AndroidFragment.newInstance("iOS"));
        fragmentList.add(AndroidFragment.newInstance("休息视频"));
        fragmentList.add(BeautyFragment.newInstance());
        SimpleFragmentPagerAdapter myAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titles);
        mBinding.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        mBinding.vpGank.setOffscreenPageLimit(0);
        myAdapter.notifyDataSetChanged();
        mBinding.tabGank.setTabMode(TabLayout.MODE_FIXED);
        mBinding.tabGank.setupWithViewPager(mBinding.vpGank);
    }




}