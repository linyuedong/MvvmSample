package com.example.library.http;



import com.example.library.base.BaseApplication;
import com.example.library.http.interceptor.logging.Level;
import com.example.library.http.interceptor.logging.LoggingInterceptor;
import com.example.library.http.interceptor.net.NetInterceptor;
import com.example.library.http.interceptor.net.NoNetInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile OkHttpClient mOkHttpClient = null;
    public static final long CONNECTION_TIMEOUT = 60;
    public static final long READ_TIMEOUT = 60;
    public static final long WRETE_TIMEOUT = 60;
    public static final int cacheSize = 10 * 1024 * 1024;
    private static Retrofit.Builder mRetrofitBuilder = null;


    private RetrofitHelper() {
        mRetrofitBuilder = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

    }

    private static class SingletonHolder {
        private static RetrofitHelper INSTANCE = new RetrofitHelper();

    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(WRETE_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
        File file = new File(BaseApplication.getContext().getCacheDir(), "Okcache");
        Cache cache = new Cache(file, cacheSize);
        builder.cache(cache);
        builder.addInterceptor(new NoNetInterceptor());
        builder.addNetworkInterceptor(new NetInterceptor());
        //日志拦截器
        builder.addInterceptor(new LoggingInterceptor
                .Builder()//构建者模式
                .loggable(BaseApplication.isDebug()) //是否开启日志打印
                .setLevel(Level.BASIC) //打印的等级
                .log(Platform.INFO) // 打印类型
                .request("Request") // request的Tag
                .response("Response")// Response的Tag
                .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                .build());

        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();

        return mOkHttpClient;
    }

    private static HashMap<Object, Object> clazzes = new HashMap<>();

    public <T> T create(String baseUrl, Class<T> clazz) {
        Object o = clazzes.get(clazz);
        if (o == null) {
            T t = mRetrofitBuilder.baseUrl(baseUrl).build().create(clazz);
            clazzes.put(clazz, t);
            return t;
        }
        return (T) o;
    }





}
