package com.pax.mvvmsample.ui.gank.beauty;

import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentBeautyBinding;
import com.pax.mvvmsample.BR;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding,BeautyViewModel> {

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
    protected void initData() {
mViewModel.test.set("BEAUTY");
    }


}
