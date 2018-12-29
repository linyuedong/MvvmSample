package com.pax.mvvmsample.databinding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.pax.mvvmsample.R;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

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
    private FrameLayout flContent;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        flContent = findViewById(R.id.content);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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




}
