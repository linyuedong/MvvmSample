package com.pax.mvvmsample.http.interceptor.net;

import android.util.Log;

import com.example.library.Utils.LogUtlis;
import com.pax.mvvmsample.Utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NoNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        if(!NetworkUtils.isNetworkConnected()){
            Request request = chain.request().newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request).newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                    .removeHeader("Pragma")
                    .build();
            LogUtlis.d("CInterceptor: response cache :"+ response.cacheResponse());
            LogUtlis.d("NoNetInterceptor: response net :"+ response.networkResponse());
            return response;
        }

        Request request = chain.request();
        Response response = chain.proceed(request);
        LogUtlis.d("CInterceptor: response cache :"+ response.cacheResponse());
        LogUtlis.d("NoNetInterceptor: response net :"+ response.networkResponse());
        return response;
    }
}

