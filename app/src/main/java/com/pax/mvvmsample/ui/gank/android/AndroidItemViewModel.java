package com.pax.mvvmsample.ui.gank.android;

import android.support.annotation.NonNull;

import com.example.library.base.ItemViewModel;
import com.pax.mvvmsample.http.bean.GankItemBean;

public class AndroidItemViewModel extends ItemViewModel<AndroidViewModel> {

    public GankItemBean bean;

    public AndroidItemViewModel(@NonNull AndroidViewModel viewModel, GankItemBean bean) {
        super(viewModel);
        this.bean = bean;
    }





}
