package com.pax.mvvmsample.ui.wanandroid.home;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import com.pax.mvvmsample.BR;
import com.example.library.base.BaseViewModel;
import com.pax.mvvmsample.R;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class HomeWandroidVM extends BaseViewModel {

    public HomeWandroidVM(@NonNull Application application) {
        super(application);
    }

    public final ObservableList<HomeWanAndroidItemVM> items = new ObservableArrayList<>();
    public final ItemBinding<HomeWanAndroidItemVM> itemBinding = ItemBinding.of(BR.item, R.layout.fragment_home_wanandroid_item);
}
