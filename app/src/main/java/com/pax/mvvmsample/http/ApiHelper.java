package com.pax.mvvmsample.http;

import com.example.library.http.RetrofitHelper;
import com.pax.mvvmsample.http.api.GankApis;
import com.pax.mvvmsample.http.api.ZhihuApis;

public class ApiHelper {

    public static GankApis getGankApis(){
        return RetrofitHelper.getInstance().create(GankApis.HOST,GankApis.class);
    }

    public static ZhihuApis getZhihuApis(){
        return RetrofitHelper.getInstance().create(ZhihuApis.HOST,ZhihuApis.class);
    }
}
