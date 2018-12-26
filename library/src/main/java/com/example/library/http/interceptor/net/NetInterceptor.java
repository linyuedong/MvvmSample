package com.example.library.http.interceptor.net;

import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.NetworkUtils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if(NetworkUtils.isNetworkConnected()){
            int maxTime = 60;
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        }
        LogUtlis.d("NetInterceptor: response cache :"+ response.cacheResponse());
        LogUtlis.d("NetInterceptor: response net :"+ response.networkResponse());
        return response;
    }
}
