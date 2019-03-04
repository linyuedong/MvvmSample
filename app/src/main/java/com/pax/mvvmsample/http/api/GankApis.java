package com.pax.mvvmsample.http.api;



import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.bean.GankSearchItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * Created by codeest on 16/8/19.
 */

public interface GankApis {

    String HOST = "http://gank.io/api/";

    /**
     * 技术文章列表
     */
    @Headers({DOMAIN_NAME_HEADER + ApiHelper.GANK_DOMAIN_NAME})
    @GET("data/{tech}/{num}/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

    /**
     * 妹纸列表
     */
    @Headers({DOMAIN_NAME_HEADER + ApiHelper.GANK_DOMAIN_NAME})
    @GET("data/福利/{num}/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);

    /**
     * 搜索
     */
    @Headers({DOMAIN_NAME_HEADER + ApiHelper.GANK_DOMAIN_NAME})
    @GET("search/query/{query}/category/{type}/count/{count}/page/{page}")
    Observable<GankHttpResponse<List<GankSearchItemBean>>> getSearchList(@Path("query") String query, @Path("type") String type, @Path("count") int num, @Path("page") int page);



}
