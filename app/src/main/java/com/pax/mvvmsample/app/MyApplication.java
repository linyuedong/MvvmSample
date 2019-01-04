package com.pax.mvvmsample.app;

import android.content.Context;
import android.os.SystemClock;

import com.example.library.base.BaseApplication;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.pax.mvvmsample.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        initCanary();
        initBugly();
    }

    private void initBugly() {
//        第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//        输出详细的Bugly SDK的Log；
//        每一条Crash都会被立即上报；
//        自定义日志将会在Logcat中输出。ll
//        建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), "5fe27d1345", !isDebug());
//        SystemClock.sleep(500);
//        CrashReport.testJavaCrash();
    }

    private void initCanary() {
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public class AppBlockCanaryContext extends BlockCanaryContext {
        // path to save log file
        @Override
        public String providePath() {
            return "/mnt/sdcard/";
        }

    }


}
