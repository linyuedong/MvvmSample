package com.pax.mvvmsample.ui.gank.beauty;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.RxUtils;
import com.example.library.base.BaseViewModel;
import com.example.library.binding.command.BindAction0;
import com.example.library.binding.command.BindAction1;
import com.example.library.bus.event.SingleLiveEvent;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;
import com.pax.mvvmsample.ui.gank.android.AndroidViewModel;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BeautyViewModel extends BaseViewModel {

    public static final int NUM = 10;
    public  int page = 1;


    public final ObservableField<String> test = new ObservableField<>();
    public BeautyViewModel(@NonNull Application application) {
        super(application);
    }
    public final ObservableList<BeautyItemViewModel> items = new ObservableArrayList<>();
    public final ItemBinding<BeautyItemViewModel> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_beauty_item);

    public SingleLiveEvent<Throwable> status = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> refreshState = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> loadMoreState = new SingleLiveEvent<>();


    public BindAction0 refreshCommand = new BindAction0() {
        @Override
        public void call() {
            page = 1;
            loadBeautyData();
        }
    };

    public BindAction0 loadMoreCommand = new BindAction0() {
        @Override
        public void call() {
            page++;
            ApiHelper.getGankApis().getGirlList(NUM,page)
                    .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                    .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                    .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(BeautyViewModel.this.getLifeCycle()))
                    .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                        @Override
                        public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                            showContentView();
                            List<GankItemBean> results = listGankHttpResponse.getResults();
                            if(results != null && results.size() > 0){
                                for(GankItemBean bean : results){
                                    items.add(new BeautyItemViewModel(bean.getUrl()));
                                }
                                loadMoreState.setValue(true);
                            }else{
                                loadMoreState.setValue(false);
                            }


                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            showErrorView();
                            if(page > 1){
                                page--;
                            }
                            loadMoreState.setValue(false);
                            status.setValue(throwable);

                        }
                    });

        }
    };


    public void loadBeautyData(){
        ApiHelper.getGankApis().getGirlList(NUM,page)
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(BeautyViewModel.this.getLifeCycle()))
                .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                    @Override
                    public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                        LogUtlis.i("SUCCESS");
                        showContentView();
                        List<GankItemBean> results = listGankHttpResponse.getResults();
                        if(results != null && results.size() > 0){
                            items.clear();
                            for(GankItemBean bean : results){
                                items.add(new BeautyItemViewModel(bean.getUrl()));

                            }
                            refreshState.setValue(true);
                        }else{
                            refreshState.setValue(false);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtlis.i("FAILD");
                        showErrorView();
                        if(page > 1){
                            page--;
                        }
                        refreshState.setValue(false);
                        status.setValue(throwable);
                    }
                });

    }

}
