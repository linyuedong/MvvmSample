package com.pax.mvvmsample.ui.gank.android;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.example.library.base.BaseViewModel;
import com.example.library.binding.command.BindAction1;
import com.example.library.bus.event.SingleLiveEvent;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.Utils.RxUtils;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AndroidViewModel extends BaseViewModel {

    public static final String TECH = "Android";
    public static final int NUM = 10;
    public  int page = 1;

    public final ObservableList<AndroidItemViewModel> items = new ObservableArrayList<>();
    public final ItemBinding<AndroidItemViewModel> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_gank_item);

    public AndroidViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<Throwable> status = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> refreshState = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> loadMoreState = new SingleLiveEvent<>();



    public final BindAction1 refreshCommand = new BindAction1<RefreshLayout>() {
        @Override
        public void call(final RefreshLayout refreshLayout) {
            page = 1;
            loadAndroidData();
        }
    };


    public final BindAction1 loadMoreCommand = new BindAction1<RefreshLayout>() {
        @Override
        public void call(final RefreshLayout refreshLayout) {
            page++;
            ApiHelper.getGankApis().getTechList(TECH,NUM,page)
                    .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                    .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                    .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(AndroidViewModel.this.getLifeCycle()))
                    .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                        @Override
                        public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                            List<GankItemBean> results = listGankHttpResponse.getResults();
                            if(transformData(results)){
                                loadMoreState.setValue(true);
                            }else {
                                loadMoreState.setValue(false);
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            page--;
                            loadMoreState.setValue(false);
                            status.setValue(throwable);

                        }
                    });
        }
    };


    public void loadAndroidData(){
        ApiHelper.getGankApis().getTechList(TECH,NUM,page)
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(AndroidViewModel.this.getLifeCycle()))
                .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                    @Override
                    public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                        List<GankItemBean> results = listGankHttpResponse.getResults();
                        if(transformData(results)){
                            refreshState.setValue(true);
                        }else {
                            refreshState.setValue(false);

                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        page--;
                        refreshState.setValue(false);
                        status.setValue(throwable);
                    }
                });


    }

    private boolean transformData(List<GankItemBean> results) {
        ArrayList<AndroidItemViewModel> gankItemBeans = new ArrayList<>();
        if(results != null && results.size() > 0){
            for(GankItemBean bean : results){
                if(bean.getImages()!= null && bean.getImages().size() > 0
                        && !TextUtils.isEmpty(bean.getImages().get(0))){
                    bean.setImage(bean.getImages().get(0));
                }else{
                    bean.setGone(true);
                }
                gankItemBeans.add(new AndroidItemViewModel(this,bean));
            }
            if(page == 1){
                items.clear();
            }
            items.addAll(gankItemBeans);
            return true;
        }
        return false;
    }


}
