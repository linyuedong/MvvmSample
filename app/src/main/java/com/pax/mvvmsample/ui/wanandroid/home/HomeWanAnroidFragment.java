package com.pax.mvvmsample.ui.wanandroid.home;

import android.os.Bundle;
import com.example.library.Utils.RxUtils;
import com.pax.mvvmsample.BR;
import com.example.library.base.BaseFragment;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.databinding.FragmentHomeWanAnroidBinding;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;
import com.youth.banner.Banner;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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
        initView();
//        ApiHelper.getWanAndroidApis().getHomeBannerList().compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxSchedulersHelper()).subscribe(new Observer<WanAndroidResponse<List<BannerBean>>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//
//            @Override
//            public void onNext(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


    }

    private void initView() {
        initRecycleView();
    }
    private Banner mBanner;
    private void initRecycleView() {
        for(int i  = 0; i < 10; i ++){
            mViewModel.items.add(new HomeWanAndroidItemVM(mViewModel,"" + i));
        }

       // BaseRecycleViewAdapter<HomeWanAndroidItemVM> mAdapter = new BaseRecycleViewAdapter<>();

//        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.banner, null));
//        mBanner = mHeaderGroup.findViewById(R.id.banner);
//        mHeaderGroup.removeView(mBanner);

        //mAdapter.addHeaderView(mBanner);
    }



}
