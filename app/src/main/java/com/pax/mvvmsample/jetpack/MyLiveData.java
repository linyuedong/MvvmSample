package com.pax.mvvmsample.jetpack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

/**
 * Created by linyd on 2018/11/29.
 */

public class MyLiveData  extends MutableLiveData<String> {

    public static final String TAG = "MyLiveData";
    private final Thread thread;
    private int count = 0;
    private boolean run = true;

    public static MyLiveData myLiveData = new MyLiveData();

    private MyLiveData(){
        thread = new Thread(new MyRunnable());
        thread.start();
    }

    public  static MyLiveData getInstance(){
        return myLiveData;
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG,"onActive");
        run = true;
        thread.interrupt();

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG,"onInactive");
        run = false;
    }


    public class MyRunnable implements  Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    if(!run)
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                myLiveData.postValue(String.valueOf(count));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
