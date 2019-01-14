package com.pax.mvvmsample.ui.gank.android;

import android.support.annotation.NonNull;
import com.example.library.base.ItemViewModel;
import com.example.library.binding.command.BindAction0;
import com.pax.mvvmsample.http.bean.GankItemBean;

public class AndroidItemViewModel extends ItemViewModel<AndroidViewModel> {

    public GankItemBean bean;

    public AndroidItemViewModel(@NonNull AndroidViewModel viewModel, GankItemBean bean) {
        super(viewModel);
        this.bean = bean;
    }

    public BindAction0 itemClick = new BindAction0() {
        @Override
        public void call() {
        }
    };

    public void test(){

    }




}
