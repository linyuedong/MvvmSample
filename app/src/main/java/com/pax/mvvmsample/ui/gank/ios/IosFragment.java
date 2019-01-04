package com.pax.mvvmsample.ui.gank.ios;


import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentIosBinding;
import com.pax.mvvmsample.BR;

public class IosFragment extends BaseFragment<FragmentIosBinding,IosViewModel> {

    public IosFragment() {
        // Required empty public constructor
    }

    public static IosFragment newInstance(){
        return new IosFragment();
    }


    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ios;
    }

    @Override
    protected void initData() {
        mViewModel.test.set("IOS");
    }
}
