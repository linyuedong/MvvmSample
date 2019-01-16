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

    public HomeWandroidVM(@NonNull Application application) {
        super(application);
    }

    public final ObservableList<HomeWanAndroidItemVM> items = new ObservableArrayList<>();
    // public final ItemBinding<HomeWanAndroidItemVM> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_home_wanandroid_item);

    public final OnItemBind<HomeWanAndroidItemVM> itemBinding = new OnItemBind<HomeWanAndroidItemVM>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, HomeWanAndroidItemVM item) {
            itemBinding.set(BR.item, position == 0 ? R.layout.head_banner : R.layout.fragment_home_wanandroid_item);
        }
    };


//    itemBind = new OnItemBindClass<>()
//            .map(String.class, BR.name, R.layout.item_name)
//  .map(Footer.class, ItemBinding.VAR_NONE, R.layout.item_footer)
//  .map(Item.class, new OnItemBind<Item>() {
//        @Override
//        public void onItemBind(ItemBinding itemBinding, int position, Item item) {
//            itemBinding.clearExtras()
//                    .set(BR.item, position == 0 ? R.layout.item_header : R.layout.item)
//                    .bindExtra(BR.extra, (list.size() - 1) == position);
//        }
//    })
//            .map(Object.class, ItemBinding.VAR_NONE, R.layout.item_other);

    public MyRecyclerViewAdapter<HomeWanAndroidItemVM> adapter = new MyRecyclerViewAdapter<HomeWanAndroidItemVM>();


    public class MyRecyclerViewAdapter<T> extends BindingRecyclerViewAdapter<T> {

        private View headView;
        private final int HEAD_VIEW_TYPE = 1;
        private final int NORMAL_ITEM_TYPE = 2;


        @Override
        public ViewDataBinding onCreateBinding(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup viewGroup) {
            ViewDataBinding binding = super.onCreateBinding(inflater, layoutId, viewGroup);
            return binding;
        }

        @Override
        public void onBindBinding(ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutId, int position, T item) {
            super.onBindBinding(binding, bindingVariable, layoutId, position, item);

        }

        private int getRealPosition(int position) {
            if (headView != null) {
                return position - 1;
            }

            return position;
        }

        @Override
        public int getItemCount() {
            return headView == null ? items.size() : items.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (headView != null && (position == 0)) {
                return HEAD_VIEW_TYPE;
            }
            return NORMAL_ITEM_TYPE;
        }


        public void setHeaderView(View view) {
            headView = view;
            notifyDataSetChanged();
        }

        public View getHeaderView() {
            return headView;

        }

    }

    public int page;


    public void loadData() {
        page = 0;
        ApiHelper.getWanAndroidApis().getHomeArticleList(page).compose(RxUtils.<WanAndroidResponse<HomeArticleBean>>rxSchedulersHelper()).subscribe(new Observer<WanAndroidResponse<HomeArticleBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WanAndroidResponse<HomeArticleBean> listWanAndroidResponse) {
                HomeArticleBean data = listWanAndroidResponse.getData();
                if(data != null){
                    List<HomeArticleBean.DatasBean> datas = data.getDatas();
                    if(datas != null&&datas.size()>0){
                       // ArrayList<String> titles = new ArrayList<>();
                        for(int i = 0; i<datas.size();i++){
                            //titles.add(datas.get(i).getTitle());
                            items.add(new HomeWanAndroidItemVM(datas.get(i).getTitle()));
                        }


                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//        Observable<WanAndroidResponse<List<BannerBean>>> homeBannerList = ApiHelper.getWanAndroidApis().getHomeBannerList();
//        Observable<WanAndroidResponse<List<HomeArticleBean>>> homeArticleList = ApiHelper.getWanAndroidApis().getHomeArticleList(page);
//
//        Observable.zip(homeBannerList, homeArticleList, new BiFunction<WanAndroidResponse<List<BannerBean>>, WanAndroidResponse<List<HomeArticleBean>>, HomeWanAndroidItemVM>() {
//            @Override
//            public HomeWanAndroidItemVM apply(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse, WanAndroidResponse<List<HomeArticleBean>> listWanAndroidResponse2) throws Exception {
//                return null;
//            }
//        }).compose(RxUtils.<HomeWanAndroidItemVM>rxSchedulersHelper())
//                .compose(RxUtils.<HomeWanAndroidItemVM>rxErrorHelper())
//                .as(RxUtils.<HomeWanAndroidItemVM>bindLifecycle(getLifeCycle()))
//                .subscribe(new Observer<HomeWanAndroidItemVM>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(HomeWanAndroidItemVM homeWanAndroidItemVM) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }


}
