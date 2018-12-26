package com.pax.mvvmsample.http.api;



import com.pax.mvvmsample.http.bean.CommentBean;
import com.pax.mvvmsample.http.bean.DailyBeforeListBean;
import com.pax.mvvmsample.http.bean.DailyListBean;
import com.pax.mvvmsample.http.bean.DetailExtraBean;
import com.pax.mvvmsample.http.bean.HotListBean;
import com.pax.mvvmsample.http.bean.SectionChildListBean;
import com.pax.mvvmsample.http.bean.SectionListBean;
import com.pax.mvvmsample.http.bean.ThemeChildListBean;
import com.pax.mvvmsample.http.bean.ThemeListBean;
import com.pax.mvvmsample.http.bean.WelcomeBean;
import com.pax.mvvmsample.http.bean.ZhihuDetailBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by codeest on 2016/8/2.
 * 知乎APIs
 */
public interface ZhihuApis {

    String HOST = "http://news-at.zhihu.com/api/4/";

    /**
     * 启动界面图片
     */
    @GET("start-image/{res}")
    Flowable<WelcomeBean> getWelcomeInfo(@Path("res") String res);

    /**
     * 最新日报
     */
    @GET("news/latest")
    Flowable<DailyListBean> getDailyList();

    /**
     * 往期日报
     */
    @GET("news/before/{date}")
    Flowable<DailyBeforeListBean> getDailyBeforeList(@Path("date") String date);

    /**
     * 主题日报
     */
    @GET("themes")
    Observable<ThemeListBean> getThemeList();

    /**
     * 主题日报详情
     */
    @GET("theme/{id}")
    Flowable<ThemeChildListBean> getThemeChildList(@Path("id") int id);

    /**
     * 专栏日报
     */
    @GET("sections")
    Flowable<SectionListBean> getSectionList();

    /**
     * 专栏日报详情
     */
    @GET("section/{id}")
    Flowable<SectionChildListBean> getSectionChildList(@Path("id") int id);

    /**
     * 热门日报
     */
    @GET("news/hot")
    Observable<HotListBean> getHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Flowable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Flowable<DetailExtraBean> getDetailExtraInfo(@Path("id") int id);

    /**
     * 日报的长评论
     */
    @GET("story/{id}/long-comments")
    Flowable<CommentBean> getLongCommentInfo(@Path("id") int id);

    /**
     * 日报的短评论
     */
    @GET("story/{id}/short-comments")
    Flowable<CommentBean> getShortCommentInfo(@Path("id") int id);

//
//    /**
//     * 获取专栏的之前消息
//     *
//     * @param id
//     * @param timestamp
//     * @return
//     */
//    @GET("section/{id}/before/{timestamp}")
//    Flowable<SectionsDetails> getBeforeSectionsDetails(@Path("id") int id, @Path("timestamp") long timestamp);
//
}
