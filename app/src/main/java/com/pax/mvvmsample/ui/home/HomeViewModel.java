package com.pax.mvvmsample.ui.home;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.example.library.base.BaseViewModel;
import com.example.library.binding.command.BindFunc1;
import com.example.library.binding.command.ResponseCommand;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.app.Constants;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }



    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public final ObservableField<String>  itemSelected = new ObservableField<>();
    }

    public ResponseCommand<MenuItem, Boolean> OnNavigationItemSelectedCommand = new ResponseCommand<MenuItem, Boolean>(new BindFunc1<MenuItem, Boolean>() {
        @Override
        public Boolean call(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_gank:
                    uc.itemSelected.set(Constants.FRAGMENT_TAG_GANK);
                    //switchContent(FRAGMENT_TAG_ZHIHUDAILY);
                    return true;
                case R.id.navigation_wanAndroid:
                    uc.itemSelected.set(Constants.FRAGMENT_TAG_WANANDEOID);
                    //switchContent(FRAGMENT_TAG_PICTURE);
                    return true;
                case R.id.navigation_dashboard:
                    uc.itemSelected.set(Constants.FRAGMENT_TAG_VIDEO);
                    //switchContent(FRAGMENT_TAG_VIDEO);
                    return true;
                case R.id.navigation_my:
                    uc.itemSelected.set(Constants.FRAGMENT_TAG_SETTING);
                    //switchContent(FRAGMENT_TAG_SETTING);
                    return true;
            }
            return false;

        }
    });



}
