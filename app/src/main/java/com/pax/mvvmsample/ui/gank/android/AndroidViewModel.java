package com.pax.mvvmsample.ui.gank.android;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.Utils.LogUtlis;
import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.Utils.RxUtils;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AndroidViewModel extends BaseViewModel {

    public static final String TECH = "Android";
    public static final int NUM = 10;
    public static int page = 0;

    public final ObservableList<GankItemBean> items = new ObservableArrayList<>();
    public final ItemBinding<GankItemBean> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_gank_item);

    public AndroidViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadAndroidData(){

        ApiHelper.getGankApis().getTechList(TECH,NUM,page)
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(this.getLifeCycle()))
                .subscribe(new Observer<GankHttpResponse<List<GankItemBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) {
                        long l = System.currentTimeMillis();
                        items.addAll(listGankHttpResponse.getResults());

                        LogUtlis.i("url = " + listGankHttpResponse.getResults().get(0).getUrl());
                        LogUtlis.i("cost = " + (System.currentTimeMillis() - l));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
