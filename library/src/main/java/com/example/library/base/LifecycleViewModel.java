package com.example.library.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.library.Utils.LogUtlis;
import com.trello.rxlifecycle2.LifecycleProvider;

public class LifecycleViewModel extends AndroidViewModel implements LifecycleObserver {

    public final String TAG = getClass().getSimpleName();
    private LifecycleOwner lifecycleOwner = null;

    public LifecycleViewModel(@NonNull Application application) {
        super(application);

    }

    @OnLifecycleEvent( Lifecycle.Event.ON_ANY)
    protected void any(){
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_CREATE)
    protected void onCreate(){
        LogUtlis.i(TAG + ":onCreate");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_START)
    protected void onStart(){
        LogUtlis.i(TAG + ":onStart");

    }

    @OnLifecycleEvent( Lifecycle.Event.ON_RESUME)
    protected void onResume(){
        LogUtlis.i(TAG + ":onResume");

    }

    @OnLifecycleEvent( Lifecycle.Event.ON_PAUSE)
    protected void onPause(){
        LogUtlis.i(TAG + ":onPause");

    }

    @OnLifecycleEvent( Lifecycle.Event.ON_STOP)
    protected void onStop(){
        LogUtlis.i(TAG + ":onStop");

    }

    @OnLifecycleEvent( Lifecycle.Event.ON_DESTROY)
    protected void onDestory(){
        LogUtlis.i(TAG + ":onDestory");

    }


    public void addLifeCycle(LifecycleOwner lifecycleOwner){
        this.lifecycleOwner = lifecycleOwner;
    }

    public LifecycleOwner getLifeCycle(){
        return lifecycleOwner;
    }

}
