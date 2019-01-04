package com.pax.mvvmsample.ui.gank.android;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;

public class AndroidViewModel extends BaseViewModel {

    public final ObservableField<String> test = new ObservableField<>();

    public AndroidViewModel(@NonNull Application application) {
        super(application);
    }


}
