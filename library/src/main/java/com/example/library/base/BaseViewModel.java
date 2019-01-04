package com.example.library.base;

import android.app.Application;
import android.support.annotation.NonNull;

public class BaseViewModel extends LifecycleViewModel {


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


}
