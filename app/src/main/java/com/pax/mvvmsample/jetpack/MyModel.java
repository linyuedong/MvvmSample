package com.pax.mvvmsample.jetpack;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by linyd on 2018/11/29.
 */

public class MyModel extends ViewModel {

    private  MutableLiveData<String> message ;

    public MutableLiveData<String> getMessage(){
        if(message == null){
            message = new MutableLiveData<>();
        }
        return message;
    }




}
