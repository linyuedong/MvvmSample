package com.pax.mvvmsample.ui.gank.daily;

import com.bumptech.glide.Glide;
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
    protected void initViewAndEvent() {
        mViewModel.test.set("DAILY");
        String url = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg";
//        Glide.with(this)
//                .asBitmap()
//                .load("http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg")
//                .into(mBinding.ivAndroidPic);
        mViewModel.url.set(url);

    }
}
