package com.pax.mvvmsample.ui.gank.beauty;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;

public class BeautyViewModel extends BaseViewModel {
    public final ObservableField<String> test = new ObservableField<>();
    public BeautyViewModel(@NonNull Application application) {
        super(application);
    }
}
