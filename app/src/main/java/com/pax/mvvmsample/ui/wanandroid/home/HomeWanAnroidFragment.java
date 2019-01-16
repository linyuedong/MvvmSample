package com.pax.mvvmsample.ui.wanandroid.home;

import com.pax.mvvmsample.BR;
import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentHomeWanAnroidBinding;



public class HomeWanAnroidFragment extends BaseFragment<FragmentHomeWanAnroidBinding,HomeWandroidVM> {


    public HomeWanAnroidFragment() {

    }

    public static HomeWanAnroidFragment newInstance() {
        HomeWanAnroidFragment fragment = new HomeWanAnroidFragment();
        return fragment;
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_wan_anroid;
    }


    @Override
    protected void initViewAndEvent() {
        showContentView();
        //SystemClock.sleep(100);
        //initView();
        mViewModel.loadData();

    }

    private void initView() {
        initRecycleView();
    }
    private void initRecycleView() {
        for(int i  = 0; i < 10; i ++){
            mViewModel.items.add(new HomeWanAndroidItemVM("11"));
        }



    }



}
