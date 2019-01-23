package com.pax.mvvmsample.ui.wanandroid.navigation;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.Utils.RxUtils;
import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.NavigationBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NavigationViewModel extends BaseViewModel {
    public NavigationViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<NaviItemViewModel> naviItemViewModels = new ObservableArrayList<NaviItemViewModel>();


    public void loadData(){
        ApiHelper.getWanAndroidApis().getNavition()
                .compose(RxUtils.<WanAndroidResponse<List<NavigationBean>>>rxSchedulersHelper())
                .compose(RxUtils.<WanAndroidResponse<List<NavigationBean>>>rxErrorHelper())
                .as(RxUtils.<WanAndroidResponse<List<NavigationBean>>>bindLifecycle(getLifeCycle()))
                .subscribe(new Observer<WanAndroidResponse<List<NavigationBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidResponse<List<NavigationBean>> response) {
                        showContentView();
                        dealData(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorSnackBar(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void dealData(WanAndroidResponse<List<NavigationBean>> response) {
        if(response!=null){
            List<NavigationBean> data = response.getData();
            if(data!=null&&data.size()>0){
                for(int i=0;i<data.size();i++){
                    NaviItemViewModel naviItemViewModel = new NaviItemViewModel();
                    NavigationBean navigationBean = data.get(i);
                    String name = navigationBean.getName();
                    naviItemViewModel.setChapterName(name);
                    List<NavigationBean.ArticlesBean> articles = navigationBean.getArticles();
                    if(articles!=null&&articles.size()>0){
                        for(int j=0;j<articles.size();j++){
                            NavigationBean.ArticlesBean articlesBean = articles.get(j);
                            String title = articlesBean.getTitle();
                            String link = articlesBean.getLink();
                            naviItemViewModel.getTitles().add(title);
                            naviItemViewModel.getUrls().add(link);
                        }
                    }
                    naviItemViewModels.add(naviItemViewModel);
                }
            }
        }
    }

}
