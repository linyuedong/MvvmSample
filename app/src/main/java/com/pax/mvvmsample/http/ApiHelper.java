package com.pax.mvvmsample.http;

import com.example.library.http.RetrofitHelper;
import com.pax.mvvmsample.http.api.GankApis;
import com.pax.mvvmsample.http.api.WanAndroidApis;
import com.pax.mvvmsample.http.cache.GankCache;

public class ApiHelper {

    public static final String GANK_DOMAIN_NAME = "gank";
    public static final String WANAndroid_DOMAIN_NAME = "wanAndroid";

    public static final String APP_GANK_DOMAIN = "http://gank.io/api/";
    public static final String APP_WANAndroid_DOMAIN = "http://www.wanandroid.com/";



    public static GankApis getGankApis(){
        return RetrofitHelper.getInstance().create(GankApis.class);
    }

    public static GankCache getGankCache(){
        return RetrofitHelper.getInstance().getCacheService(GankCache.class);
    }

    public static WanAndroidApis getWanAndroidApis(){
        return RetrofitHelper.getInstance().create(WanAndroidApis.class);
    }



}
