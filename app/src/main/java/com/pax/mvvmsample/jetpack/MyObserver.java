package com.pax.mvvmsample.jetpack;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by linyd on 2018/11/28.
 */

@SuppressWarnings("ALL")
public class MyObserver implements LifecycleObserver {

    public static final String TAG = "MyObserver";

    @OnLifecycleEvent( Lifecycle.Event.ON_ANY)
    void any(){
        Log.i(TAG,"any");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_CREATE)
    void onCreate(){
        Log.i(TAG,"onCreate");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_START)
    void onStart(){
        Log.i(TAG,"onStart");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_RESUME)
    void onResume(){
        Log.i(TAG,"onResume");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_PAUSE)
    void onPause(){
        Log.i(TAG,"onPause");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_STOP)
    void onStop(){
        Log.i(TAG,"onStop");
    }

    @OnLifecycleEvent( Lifecycle.Event.ON_DESTROY)
    void onDestory(){
        Log.i(TAG,"onDestory");
    }


}
