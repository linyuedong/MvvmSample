package com.pax.mvvmsample.ui.gank.daily;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentDailyBinding;
import com.pax.mvvmsample.BR;


public class DailyFragment extends BaseFragment<FragmentDailyBinding,DailyViewModel> {


    public DailyFragment() {

    }

    public static DailyFragment newInstance(){
        return new DailyFragment();
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initData() {
        mViewModel.test.set("DAILY");
    }
}
