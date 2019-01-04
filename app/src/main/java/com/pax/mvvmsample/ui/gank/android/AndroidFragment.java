package com.pax.mvvmsample.ui.gank.android;


import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentAndroidBinding;
import com.pax.mvvmsample.BR;

public class AndroidFragment extends BaseFragment<FragmentAndroidBinding,AndroidViewModel> {


    public AndroidFragment() {
    }

    public static AndroidFragment newInstance(){
        return  new AndroidFragment();
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
    protected void initData() {
    mViewModel.test.set("ANDROID");
    }
}
