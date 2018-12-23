package com.example.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.library.Utils.LogUtlis;

public class BaseApplication extends Application {

    private static Application mApplication;
    private static boolean debug = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initDebugStatus();
        initActivityLifecycle();
    }


    public static boolean isDebug(){
        return debug;
    }

    private void initDebugStatus() {
        debug = getApplicationInfo() != null && (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i("aa","debug = " + debug);
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
                LogUtlis.d("onDestroy : " + activity.getClass().getSimpleName());
                AppManager.getAppManager().finishActivity(activity);
            }
        });
    }

    public static Application getContext(){
        return mApplication;
    }
}
