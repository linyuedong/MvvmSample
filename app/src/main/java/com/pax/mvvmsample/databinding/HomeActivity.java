package com.pax.mvvmsample.databinding;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.example.library.base.BaseActivity;
import com.pax.mvvmsample.BR;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.ui.gank.GankFragment;

public class HomeActivity extends BaseActivity<ActivityHomeBinding,HomeViewModel> {

    private FrameLayout flContent;
    private BottomNavigationView navigation;
    private String currentFragmentTag ;
    public static final String FRAGMENT_TAG_ZHIHUDAILY = "ZhiHuDaily";
    public static final String FRAGMENT_TAG_PICTURE = "Picture";
    public static final String FRAGMENT_TAG_VIDEO = "Video";
    public static final String FRAGMENT_TAG_SETTING = "Setting";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchContent(FRAGMENT_TAG_ZHIHUDAILY);
                    return true;
                case R.id.navigation_home2:

                    switchContent(FRAGMENT_TAG_PICTURE);
                    return true;
                case R.id.navigation_dashboard:
                    switchContent(FRAGMENT_TAG_VIDEO);
                    return true;
                case R.id.navigation_notifications:
                    switchContent(FRAGMENT_TAG_SETTING);
                    return true;
            }
            return false;
        }
    };



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
        navigation = mBinding.navigation;
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchContent(FRAGMENT_TAG_ZHIHUDAILY);
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
                case FRAGMENT_TAG_ZHIHUDAILY:
                    tagetFragment = GankFragment.newInstance();
                    break;
                case FRAGMENT_TAG_PICTURE:
                    tagetFragment = Test1Fragment.newInstance();
                    break;
                case FRAGMENT_TAG_VIDEO:
                    tagetFragment = GankFragment.newInstance();
                    break;
                case FRAGMENT_TAG_SETTING:
                    tagetFragment = Test1Fragment.newInstance();
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
