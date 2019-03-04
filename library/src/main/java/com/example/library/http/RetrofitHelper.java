package com.example.library.http;



import com.example.library.base.BaseApplication;
import com.example.library.base.BaseConstants;
import com.example.library.http.interceptor.logging.Level;
import com.example.library.http.interceptor.logging.LoggingInterceptor;
import com.example.library.http.interceptor.net.NetInterceptor;
import com.example.library.http.interceptor.net.NoNetInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile OkHttpClient mOkHttpClient = null;
    private static Retrofit mRetrofit= null;
    private static RxCache mRxCache = null;
    public static final long CONNECTION_TIMEOUT = 60;
    public static final long READ_TIMEOUT = 60;
    public static final long WRETE_TIMEOUT = 60;
    public static final int cacheSize = 10 * 1024 * 1024;
    private static final String DEFAULT_BASE_URL = "http://www.baidu.com/";

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(DEFAULT_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRxCache = new RxCache.Builder().persistence(BaseApplication.getContext().getExternalCacheDir(), new GsonSpeaker());
              
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
//        //设置缓存
//        File file = new File(BaseApplication.getContext().getCacheDir(), "Okcache");
//        Cache cache = new Cache(file, cacheSize);
//        builder.cache(cache);
//        builder.addInterceptor(new NoNetInterceptor());
//        builder.addNetworkInterceptor(new NetInterceptor());
        //日志拦截器
        builder.addInterceptor(new LoggingInterceptor
                .Builder()//构建者模式
                .loggable(BaseApplication.isDebug()) //是否开启日志打印
                .setLevel(Level.BASIC) //打印的等级
                .log(Platform.INFO) // 打印类型
                .request(BaseConstants.globleTag + "-Request") // request的Tag
                .response(BaseConstants.globleTag + "-Response")// Response的Tag
                .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                .build());

        //错误重连
        builder.retryOnConnectionFailure(true);
        //mOkHttpClient = builder.build();
        mOkHttpClient = RetrofitUrlManager.getInstance().with(builder).build();
        return mOkHttpClient;
    }

//    private static HashMap<Object, Object> clazzes = new HashMap<>();
//
//    public <T> T create(String baseUrl, Class<T> clazz) {
//        Object o = clazzes.get(clazz);
//        if (o == null) {
//            T t = mRetrofitBuilder.baseUrl(baseUrl).build().create(clazz);
//            clazzes.put(clazz, t);
//            return t;
//        }
//        return (T) o;
//    }


    private static HashMap<String, Object> mRetrofitServiceCache = new HashMap<>();

    public <T> T create(Class<T> serviceClass) {
        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }


    private static HashMap<String, Object> mCacheServiceCache = new HashMap<>();

    public <T> T getCacheService(Class<T> cacheClass) {
        T cacheService = (T) mCacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.using(cacheClass);
            mCacheServiceCache.put(cacheClass.getCanonicalName(), cacheService);
        }
        return cacheService;
    }



}
