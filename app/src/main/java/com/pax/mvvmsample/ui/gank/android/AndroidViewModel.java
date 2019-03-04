package com.pax.mvvmsample.ui.gank.android;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.RxUtils;
import com.example.library.base.BaseViewModel;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.example.library.binding.command.BindAction0;
import com.example.library.bus.event.SingleLiveEvent;
import com.example.library.http.RetrofitHelper;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.GankItemBean;
import com.pax.mvvmsample.http.response.GankHttpResponse;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AndroidViewModel extends BaseViewModel {

    public String TECH;
    public static final int NUM = 10;
    public int page = 1;

    public final ObservableList<AndroidItemViewModel> items = new ObservableArrayList<>();
    public final ItemBinding<AndroidItemViewModel> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_android_item);

    public AndroidViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<Throwable> status = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> refreshState = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> loadMoreState = new SingleLiveEvent<>();

    public BaseRecycleViewAdapter<AndroidItemViewModel> adapter = new BaseRecycleViewAdapter<AndroidItemViewModel>();

    public final BindAction0 refreshCommand = new BindAction0() {
        @Override
        public void call() {
            page = 1;
            loadAndroidData();
        }
    };


    public final BindAction0 loadMoreCommand = new BindAction0() {
        @Override
        public void call() {
            page++;
            loadMoreData();

        }
    };

    private void loadMoreData() {
        Observable<GankHttpResponse<List<GankItemBean>>> techList = ApiHelper.getGankApis().getTechList(TECH, NUM, page);
        Observable<GankHttpResponse<List<GankItemBean>>> techListCache = ApiHelper.getGankCache().getTechList(techList, new DynamicKey(TECH+page), new EvictDynamicKey (false));
        techListCache.compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(AndroidViewModel.this.getLifeCycle()))
                .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                    @Override
                    public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                        showContentView();
                        List<GankItemBean> results = listGankHttpResponse.getResults();
                        if (transformData(results)) {

                            finishLoadMore(true);
                        } else {


                            finishLoadMore(false);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showErrorView();
                        if (page > 1) {
                            page--;
                        }
                        finishLoadMore(false);
                        //showErrorSnackBar(throwable);

                    }
                });
    }


    public void loadAndroidData() {
        Observable<GankHttpResponse<List<GankItemBean>>> techList = ApiHelper.getGankApis().getTechList(TECH, NUM, page);
        Observable<GankHttpResponse<List<GankItemBean>>> techListCache = ApiHelper.getGankCache().getTechList(techList, new DynamicKey(TECH+page), new EvictDynamicKey(true));
        techListCache.compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxErrorHelper())
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulersHelper())
                .as(RxUtils.<GankHttpResponse<List<GankItemBean>>>bindLifecycle(AndroidViewModel.this.getLifeCycle()))
                .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
                    @Override
                    public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
                        LogUtlis.i("" + AndroidViewModel.this.hashCode());
                        showContentView();
                        List<GankItemBean> results = listGankHttpResponse.getResults();
                        if (transformData(results)) {
                            finishRefresh(true);
                        } else {
                            refreshState.setValue(false);
                            finishRefresh(false);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showErrorView();
                        if (page > 1) {
                            page--;
                        }
                        finishRefresh(false);
                        //showErrorSnackBar(throwable);
                    }
                });


    }

    private boolean transformData(List<GankItemBean> results) {
        ArrayList<AndroidItemViewModel> gankItemBeans = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (GankItemBean bean : results) {
                if (bean.getImages() != null && bean.getImages().size() > 0
                        && !TextUtils.isEmpty(bean.getImages().get(0))) {
                    bean.setImage(bean.getImages().get(0));
                } else {
                    bean.setGone(true);
                }
                gankItemBeans.add(new AndroidItemViewModel(this, bean));
            }
            if (page == 1) {
                items.clear();
            }
            items.addAll(gankItemBeans);
            return true;
        }
        return false;
    }

    public void setType(String tpye) {
        TECH = tpye;
    }


}
