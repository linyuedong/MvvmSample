package com.pax.mvvmsample;



import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.library.Utils.RxUtils;
import com.noober.background.BackgroundLibrary;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;
import com.pax.mvvmsample.jetpack.MyLiveData;
import com.pax.mvvmsample.jetpack.MyModel;
import com.pax.mvvmsample.jetpack.MyObserver;
import com.pax.mvvmsample.ui.home.HomeActivity;
import com.example.library.base.adpter.MyBaseBindingRecyclerViewAdapter;

import com.pax.mvvmsample.ui.wanandroid.home.HomeWanAndroidItemVM;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity  {

    @SuppressLint("AutoDispose")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1 = (Button) findViewById(R.id.bt1);
        final TextView tv1 = (TextView) findViewById(R.id.tv1);
        Button bt2 = findViewById(R.id.bt2);

        //LifeCycle
        getLifecycle().addObserver(new MyObserver());

        //ViewModel
        final MyModel myModel = ViewModelProviders.of(this).get(MyModel.class);
        myModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv1.setText(s);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myModel.getMessage().setValue("Hello LiveData");
            }
        });

        //LiveData
        MyLiveData.getInstance().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv1.setText(s);
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
        Test.test();
        testDetail();

    }

    private void testDetail() {
        TextView tvComment = findViewById(R.id.tvComment);
        ImageView ivMessage = findViewById(R.id.ivMessage);
        ImageView ivCollection = findViewById(R.id.iv_collection);
        ImageView ivLike = findViewById(R.id.iv_like);
        ImageView ivSend = findViewById(R.id.iv_send);

        ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
