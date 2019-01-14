package com.example.library.base;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.library.bus.event.SingleLiveEvent;

public class BaseViewModel extends LifecycleViewModel {

    public  UIStatus mUIStatus = new UIStatus();
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


   public void showContentView(){
       mUIStatus.showContentLiveData.setValue(true);
   }

   public void showLoaddingView(){
       mUIStatus.showLoaddingtLiveData.setValue(true);
   }

   public void showErrorView(){
       mUIStatus.showErrorLiveData.setValue(true);
   }

   

    class UIStatus{
        public SingleLiveEvent<Boolean> showContentLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> showLoaddingtLiveData = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> showErrorLiveData = new SingleLiveEvent<>();

        public SingleLiveEvent<Throwable> status = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> refreshState = new SingleLiveEvent<>();
        public SingleLiveEvent<Boolean> loadMoreState = new SingleLiveEvent<>();

    }




}
