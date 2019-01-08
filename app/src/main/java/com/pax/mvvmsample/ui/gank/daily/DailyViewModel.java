package com.pax.mvvmsample.ui.gank.daily;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.library.base.BaseViewModel;

public class DailyViewModel extends BaseViewModel {
    public final ObservableField<String> test = new ObservableField<>();
    public final ObservableField<String> url = new ObservableField<>();

    public DailyViewModel(@NonNull Application application) {
        super(application);
    }
}
