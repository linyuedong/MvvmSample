package com.pax.mvvmsample.http;

import android.util.Log;

import com.pax.mvvmsample.BuildConfig;
import com.pax.mvvmsample.app.MyApplication;
import com.pax.mvvmsample.http.interceptor.logging.Level;
import com.pax.mvvmsample.http.interceptor.logging.LoggingInterceptor;
import com.pax.mvvmsample.http.interceptor.net.NetInterceptor;
import com.pax.mvvmsample.http.interceptor.net.NoNetInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile OkHttpClient mOkHttpClient = null;
    public static final long  CONNECTION_TIMEOUT = 60 ;
    public static final long  READ_TIMEOUT = 60;
    public static final long  WRETE_TIMEOUT = 60;
    public static final int cacheSize = 10 * 1024 * 1024;

    private static OkHttpClient getOkHttpClient(){
        if(mOkHttpClient == null){
            synchronized (RetrofitHelper.class){
                if(mOkHttpClient == null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    //设置超时
                    builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
                    builder.writeTimeout(READ_TIMEOUT,TimeUnit.SECONDS);
                    builder.readTimeout(WRETE_TIMEOUT,TimeUnit.SECONDS);
                    //设置缓存
                    File file = new File(MyApplication.getContext().getCacheDir(), "Okcache");
                    Cache cache = new Cache(file, cacheSize);
                    builder.cache(cache);
                    builder.addInterceptor(new NoNetInterceptor());
                    builder.addNetworkInterceptor(new NetInterceptor());
                    //日志拦截器
                    builder.addInterceptor(new LoggingInterceptor
                            .Builder()//构建者模式
                            .loggable(BuildConfig.DEBUG) //是否开启日志打印
                            .setLevel(Level.BASIC) //打印的等级
                            .log(Platform.INFO) // 打印类型
                            .request("Request") // request的Tag
                            .response("Response")// Response的Tag
                            .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                            .build());

                    //错误重连
                    builder.retryOnConnectionFailure(true);
                    mOkHttpClient =  builder.build();
                }
            }
        }
        return mOkHttpClient;
    }


    public static<T> T create(Class<T> clazz){
        Retrofit retrofit=new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl("http://wanandroid.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
