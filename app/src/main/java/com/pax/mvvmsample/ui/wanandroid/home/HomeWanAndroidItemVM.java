package com.pax.mvvmsample.ui.wanandroid.home;

import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;
import com.example.library.base.ItemViewModel;

public class HomeWanAndroidItemVM extends ItemViewModel {


    public String test;
    public HomeWanAndroidItemVM(@NonNull BaseViewModel viewModel,String test) {
        super(viewModel);
        test = test;
    }


}
