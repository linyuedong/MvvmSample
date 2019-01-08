package com.example.library.http.interceptor.net;

import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.NetworkUtils;


import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NoNetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        if(!NetworkUtils.isNetworkConnected()){
            Request request = chain.request();
//            Request request = chain.request().newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
            Response response = chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                    .build();
            LogUtlis.d("NoNetInterceptor: response cache :"+ response.cacheResponse());
            LogUtlis.d("NoNetInterceptor: response net :"+ response.networkResponse());
            return response;
        }


        return chain.proceed(chain.request());
    }
}

