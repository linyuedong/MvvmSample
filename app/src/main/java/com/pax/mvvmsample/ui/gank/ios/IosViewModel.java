package com.pax.mvvmsample.ui.gank.ios;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;

public class IosViewModel extends BaseViewModel {
    public final ObservableField<String> test = new ObservableField<>();

    public IosViewModel(@NonNull Application application) {
        super(application);
    }



}
