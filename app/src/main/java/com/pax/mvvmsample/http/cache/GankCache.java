package com.pax.mvvmsample.http.cache;

import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

public interface GankCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<GankHttpResponse<List<GankItemBean>>> getTechList(Observable<GankHttpResponse<List<GankItemBean>>> techList ,DynamicKey idLastUserQueried, EvictProvider evictProvider);

}
