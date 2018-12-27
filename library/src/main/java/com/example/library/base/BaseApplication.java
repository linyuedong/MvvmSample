package com.example.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.library.Utils.LogUtlis;
import com.github.naturs.logger.Logger;
import com.github.naturs.logger.adapter.DefaultLogAdapter;
import com.github.naturs.logger.android.adapter.AndroidLogAdapter;
import com.github.naturs.logger.android.strategy.converter.AndroidLogConverter;


public class BaseApplication extends Application {

    private static Application mApplication;
    private static boolean debug = false;
    private static final String globleTag = "abc";

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initDebugStatus();
        initLogger();
        initActivityLifecycle();
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

        Logger.addLogAdapter(new AndroidLogAdapter(globleTag,false));
        Logger.setLogConverter(new AndroidLogConverter());

        LogUtlis.setGlobleTag(globleTag);

    }


    public static boolean isDebug(){
        return debug;
    }

    private void initDebugStatus() {
        debug = getApplicationInfo() != null && (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("abc","debug = " + debug);
    }


    private void initActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                LogUtlis.d("onCreate : " + activity.getClass().getSimpleName());
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtlis.d("onActivityDestroyed : " + activity.getClass().getSimpleName());
                AppManager.getAppManager().finishActivity(activity);
            }
        });
    }

    public static Application getContext(){
        return mApplication;
    }



}
