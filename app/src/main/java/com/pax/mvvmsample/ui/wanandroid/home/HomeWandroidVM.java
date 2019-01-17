package com.pax.mvvmsample.ui.wanandroid.home;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.Utils.RxUtils;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.example.library.binding.command.BindAction0;
import com.example.library.bus.event.SingleLiveEvent;
import com.pax.mvvmsample.BR;
import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.HomeArticleBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;
import com.pax.mvvmsample.ui.gank.android.AndroidItemViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass;

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
                HomeArticleBean data = listWanAndroidResponse.getData();
                if (data != null) {
                    List<HomeArticleBean.DatasBean> datas = data.getDatas();
                    if (datas != null && datas.size() > 0) {
                        for (int i = 0; i < datas.size(); i++) {
                            items.add(new HomeWanAndroidItemVM(datas.get(i).getTitle()));
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
                HomeArticleBean data = listWanAndroidResponse.getData();
                if (data != null) {
                    List<HomeArticleBean.DatasBean> datas = data.getDatas();
                    if (datas != null && datas.size() > 0) {
                        items.clear();
                        for (int i = 0; i < datas.size(); i++) {
                           items.add(new HomeWanAndroidItemVM(datas.get(i).getTitle()));
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
