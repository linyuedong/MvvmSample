package com.pax.mvvmsample.ui.wanandroid.home;

import android.databinding.Observable;
import android.databinding.ObservableField;

public class HeadtestVM {


    public final ObservableField<String> tv = new ObservableField<>();

    public HeadtestVM(String s){
        tv.set(s);

    }
}
