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

import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity  {

    @SuppressLint("AutoDispose")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        //test();
        testRecycle();

    }

    private void testRecycle() {
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
////        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
//////设置布局管理器
////        recyclerView.setLayoutManager(layoutManager);
//////设置为垂直布局，这也是默认的
////        layoutManager.setOrientation(OrientationHelper. VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        final MyBaseBindingRecyclerViewAdapter<HomeWanAndroidItemVM> adapter = new MyBaseBindingRecyclerViewAdapter<HomeWanAndroidItemVM>(this) {
//            @Override
//            protected int getLayoutResId(int viewType) {
//                return R.layout.mytest;
//            }
//
//            @Override
//            protected int getItemVariableId() {
//                return BR.test;
//            }
//        };
//
//        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(this).inflate(R.layout.head_banner, null));
//        final Banner banner = mHeaderGroup.findViewById(R.id.test_banner);
//        mHeaderGroup.removeView(banner);
//        adapter.setHeaderView(banner);
//        recyclerView.setAdapter(adapter);
//
//
//        ApiHelper.getWanAndroidApis().getHomeBannerList().compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxSchedulersHelper()).subscribe(new io.reactivex.Observer<WanAndroidResponse<List<BannerBean>>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse) {
//                //view.setText("haha");
//                List<BannerBean> data = listWanAndroidResponse.getData();
//                if(data != null && data.size() > 0){
//                    ArrayList<String> imageUrls = new ArrayList<>();
//                    ArrayList<String> titles = new ArrayList<>();
//
//                    for(int i = 0; i < data.size();i ++){
//                        imageUrls.add(data.get(i).getImagePath());
//                        titles.add(data.get(i).getTitle());
//                        adapter.getItems().add(new HomeWanAndroidItemVM(data.get(i).getTitle()));
//                    }
//
//
//                    //设置banner样式
//                    banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
//                    //设置图片加载器
//                    banner.setImageLoader(new GlideImageLoader());
//                    //设置图片集合
//                    banner.setImages(imageUrls);
//                    //banner设置方法全部调用完毕时最后调用
//                    //设置banner动画效果
//                    banner.setBannerAnimation(Transformer.DepthPage);
//                    //设置标题集合（当banner样式有显示title时）
//                    banner.setBannerTitles(titles);
//                    //设置自动轮播，默认为true
//                    banner.isAutoPlay(true);
//                    //设置轮播时间
//                    banner.setDelayTime(1500);
//                    //设置指示器位置（当banner模式中有指示器时）
//                    banner.setIndicatorGravity(BannerConfig.CENTER);
//                    //banner设置方法全部调用完毕时最后调用
//                    banner.start();
//                    adapter.notifyDataSetChanged();
//
//                }
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


    public void test(){
        //Toast.makeText(this,"hh",Toast.LENGTH_SHORT).show();



    }



}
