package com.pax.mvvmsample.ui.wanandroid.home;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.Utils.RxUtils;
import com.example.library.base.BaseViewModel;
import com.example.library.binding.command.BindAction0;
import com.example.library.bus.event.SingleLiveEvent;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.HomeArticleBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomeWandroidVM extends BaseViewModel {

    public int page = 0;

    public HomeWandroidVM(@NonNull Application application) {
        super(application);
    }

    public final ObservableList<HomeWanAndroidItemVM> items = new ObservableArrayList<>();

    public SingleLiveEvent<List<BannerBean>> bannerData = new SingleLiveEvent<>();

    public final BindAction0 refreshCommand = new BindAction0() {
        @Override
        public void call() {
            page = 0;
            loadData();
        }
    };

    public final BindAction0 loadMoreCommand = new BindAction0() {
        @Override
        public void call() {
            page++;
            getMoreArtcile();
        }
    };

    private void getMoreArtcile() {
        ApiHelper.getWanAndroidApis().getHomeArticleList(page).compose(RxUtils.<WanAndroidResponse<HomeArticleBean>>rxSchedulersHelper()).subscribe(new Observer<WanAndroidResponse<HomeArticleBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WanAndroidResponse<HomeArticleBean> listWanAndroidResponse) {
                showContentView();
                HomeArticleBean homeArticleBean = listWanAndroidResponse.getData();
                if (homeArticleBean != null) {
                    List<HomeArticleBean.DatasBean> datas = homeArticleBean.getDatas();
                    if (datas != null && datas.size() > 0) {
                        for (int i = 0; i < datas.size(); i++) {
                            HomeArticleBean.DatasBean data = datas.get(i);
                            items.add(new HomeWanAndroidItemVM(data.getTitle(),data.getAuthor(),data.getChapterName(),data.getSuperChapterName(),data.getNiceDate(),data.getLink()));
                        }
                        finishLoadMore(true);
                    } else {
                        finishLoadMore(false);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                showErrorView();
                finishLoadMore(false);
                showErrorSnackBar(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void loadData() {
        getBanner();
        getArtcile();
    }

    private void getArtcile() {
        ApiHelper.getWanAndroidApis().getHomeArticleList(page).compose(RxUtils.<WanAndroidResponse<HomeArticleBean>>rxSchedulersHelper()).subscribe(new Observer<WanAndroidResponse<HomeArticleBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WanAndroidResponse<HomeArticleBean> listWanAndroidResponse) {
                showContentView();
                HomeArticleBean homeArticleBean = listWanAndroidResponse.getData();
                if (homeArticleBean != null) {
                    List<HomeArticleBean.DatasBean> datas = homeArticleBean.getDatas();
                    if (datas != null && datas.size() > 0) {
                        items.clear();
                        for (int i = 0; i < datas.size(); i++) {
                            HomeArticleBean.DatasBean data = datas.get(i);
                            items.add(new HomeWanAndroidItemVM(data.getTitle(),data.getAuthor(),data.getChapterName(),data.getSuperChapterName(),data.getNiceDate(),data.getLink()));
                        }
                        finishRefresh(true);
                    } else {
                        finishRefresh(false);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                showErrorView();
                finishRefresh(false);
                showErrorSnackBar(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void getBanner() {
        ApiHelper.getWanAndroidApis().getHomeBannerList()
                .compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxSchedulersHelper())
                .compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxErrorHelper())
                .as(RxUtils.<WanAndroidResponse<List<BannerBean>>>bindLifecycle(getLifeCycle()))
                .subscribe(new Observer<WanAndroidResponse<List<BannerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse) {
                        if (listWanAndroidResponse != null) {
                            showContentView();
                            List<BannerBean> data = listWanAndroidResponse.getData();
                            if (data != null && data.size() > 0) {
                                bannerData.setValue(data);
                                finishRefresh(true);
                            } else {
                                finishRefresh(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorView();
                        finishRefresh(false);
                        showErrorSnackBar(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
