package com.pax.mvvmsample.databinding;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.Toast;

public class LoginViewModel extends ViewModel {
    public static final String TAG = LoginViewModel.class.getSimpleName();

    public final ObservableField<String> account = new ObservableField<>();
    public final ObservableField<String> passWord = new ObservableField<>();


    public void reset(){
        Log.i(TAG,"account = " + account.get() + " passWord = " + passWord.get());
        account.set("");
        passWord.set("");
    }


    public void login(){

    }


}
