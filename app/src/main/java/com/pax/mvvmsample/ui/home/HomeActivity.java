package com.pax.mvvmsample.ui.home;

import android.databinding.Observable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.library.Utils.LogUtlis;
import com.example.library.base.BaseActivity;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.app.Constants;
import com.pax.mvvmsample.databinding.ActivityHomeBinding;

import com.pax.mvvmsample.ui.gank.GankFragment;
import com.pax.mvvmsample.ui.wanandroid.WanAndroidFragment;

public class HomeActivity extends BaseActivity<ActivityHomeBinding,HomeViewModel> {

    private FrameLayout flContent;
    private BottomNavigationView navigation;
    private String currentFragmentTag ;



    @Override
    protected int initBR() {
        return BR.vm;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViewAndData() {
        switchContent(Constants.FRAGMENT_TAG_ZHIHUDAILY);

   mViewModel.uc.itemSelected.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
       @Override
       public void onPropertyChanged(Observable sender, int propertyId) {
           String fragmentName = mViewModel.uc.itemSelected.get();
           LogUtlis.i("fragmentName = " + fragmentName);
           switchContent(fragmentName);
       }
   });

    }



    private void switchContent(String tag) {
        //点击相同Item不做处理
        if (currentFragmentTag != null && currentFragmentTag.equals(tag))
            return;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        //隐藏当前Fragment
        Fragment currentFragment = mFragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment tagetFragment = mFragmentManager.findFragmentByTag(tag);
        if (tagetFragment == null) {
            switch (tag) {
                case Constants.FRAGMENT_TAG_ZHIHUDAILY:
                    tagetFragment = GankFragment.newInstance();
                    break;
                case Constants.FRAGMENT_TAG_PICTURE:
                    tagetFragment = WanAndroidFragment.newInstance("");
                    break;
                case Constants.FRAGMENT_TAG_VIDEO:
                    tagetFragment = GankFragment.newInstance();
                    break;
                case Constants.FRAGMENT_TAG_SETTING:
                    tagetFragment = GankFragment.newInstance();
                    break;
            }
        }

        if(tagetFragment != null){
            if(!tagetFragment.isAdded()){
                ft.add(R.id.content, tagetFragment, tag);
            }
            ft.show(tagetFragment);
        }
        ft.commit();
        currentFragmentTag = tag;
    }


    public void setActionBarTitle(String title){
         getSupportActionBar().setTitle(title);
    }




}
