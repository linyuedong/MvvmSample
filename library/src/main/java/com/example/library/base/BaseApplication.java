package com.example.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.library.Utils.Utils;
import com.example.library.Utils.LogUtlis;
import com.example.library.component.ActivityLifecycle;
import com.github.naturs.logger.Logger;
import com.github.naturs.logger.android.adapter.AndroidLogAdapter;
import com.github.naturs.logger.android.strategy.converter.AndroidLogConverter;


public class BaseApplication extends Application {

    private static Application mApplication;
    private static boolean debug = false;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initDebugStatus();
        initLogger();
        //registerActivityLifecycleCallbacks(new ActivityLifecycle());
        Utils.init(mApplication);
    }

    private void initLogger() {
//        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)
//                .tag("abc")
//                .build()));
//        Logger.addLogAdapter(new AndroidLogAdapter() {
//            @Override public boolean isLoggable(int priority, String tag) {
//                 return BuildConfig.DEBUG;
//            }
//        });

        Logger.addLogAdapter(new AndroidLogAdapter(BaseConstants.globleTag,false));
        Logger.setLogConverter(new AndroidLogConverter());

        LogUtlis.setGlobleTag(BaseConstants.globleTag);

    }


    public static boolean isDebug(){
        return debug;
    }

    private void initDebugStatus() {
        debug = getApplicationInfo() != null && (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("abc","debug = " + debug);
    }




    public static Application getContext(){
        return mApplication;
    }



}
