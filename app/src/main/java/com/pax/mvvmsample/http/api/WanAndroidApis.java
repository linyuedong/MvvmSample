package com.pax.mvvmsample.http.api;

import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.HomeArticleBean;
import com.pax.mvvmsample.http.bean.wanAndroid.NavigationBean;
import com.pax.mvvmsample.http.bean.wanAndroid.TreeBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WanAndroidApis {
    public static final String BASE_URL = "http://www.wanandroid.com/";


    @GET("article/list/{page}/json")
    Observable<WanAndroidResponse<HomeArticleBean>> getHomeArticleList(@Path("page") int page);

    @GET("banner/json")
    Observable<WanAndroidResponse<List<BannerBean>>> getHomeBannerList();

    @GET("tree/json")
    Observable<WanAndroidResponse<List<TreeBean>>> getTree();

    @GET("navi/json")
    Observable<WanAndroidResponse<List<NavigationBean>>> getNavition();




}
