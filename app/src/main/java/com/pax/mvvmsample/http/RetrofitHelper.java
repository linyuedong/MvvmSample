package com.pax.mvvmsample.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitHelper {

    private static volatile OkHttpClient mOkHttpClient = null;

    private static OkHttpClient getOkHttpClient(){
        if(mOkHttpClient == null){
            synchronized (RetrofitHelper.class){
                if(mOkHttpClient == null){
                    mOkHttpClient=new OkHttpClient.Builder()
                            .addInterceptor(new OkHttpInterceptor())
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(10,TimeUnit.SECONDS)
                            .writeTimeout(10,TimeUnit.SECONDS)
                            .cache(cache)
                            .build();
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
