package com.pax.mvvmsample;



import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.RxUtils;
import com.example.library.base.adpter.BaseRecycleViewAdapter;
import com.pax.mvvmsample.databinding.TestHeadBinding;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.wanAndroid.BannerBean;
import com.pax.mvvmsample.http.bean.wanAndroid.WanAndroidResponse;
import com.pax.mvvmsample.jetpack.MyLiveData;
import com.pax.mvvmsample.jetpack.MyModel;
import com.pax.mvvmsample.jetpack.MyObserver;
import com.pax.mvvmsample.ui.home.HomeActivity;
import com.pax.mvvmsample.ui.wanandroid.home.BindingRecyclerViewAdapter;
import com.pax.mvvmsample.ui.wanandroid.home.HeadtestVM;
import com.pax.mvvmsample.ui.wanandroid.home.HomeWanAndroidItemVM;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



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


        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://wwww.baidu.com";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url)
                        .get()//默认就是GET请求，可以不写
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        LogUtlis.i( "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtlis.i("onResponse: " + response.body().string());
                    }
                });


            }
        }).start();

        ImageView iv = findViewById(R.id.iv);
        String url = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg";
        //Glide.with(this).load(url).into(iv);

        //test();
        testRecycle();

    }

    private void testRecycle() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
//设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        final HeadtestVM aa = new HeadtestVM("aa");
        ArrayList<HomeWanAndroidItemVM> strings = new ArrayList<>();
        strings.add(new HomeWanAndroidItemVM("aa"));
        strings.add(new HomeWanAndroidItemVM("aa"));
        strings.add(new HomeWanAndroidItemVM("aa"));
        strings.add(new HomeWanAndroidItemVM("aa"));
        final BindingRecyclerViewAdapter<HomeWanAndroidItemVM> adapter = new BindingRecyclerViewAdapter<>(this, strings, R.layout.mytest,BR.test);
        adapter.setHeaderView(R.layout.test_head, aa,BR.headVM);
        recyclerView.setAdapter(adapter);

        LogUtlis.i("+++++++++++");

        ViewDataBinding headBinding = adapter.mHeadBinding;
        if(headBinding != null){
            LogUtlis.i("headBinding != null");
        }else{
            LogUtlis.i("headBinding == null");
        }
//        final TextView view = adapter.getHeaderView().findViewById(R.id.test_tv);
//        final Banner banner = adapter.getHeaderView().findViewById(R.id.test_banner);
//
        aa.tv.set("hehe");


//        ApiHelper.getWanAndroidApis().getHomeBannerList().compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxSchedulersHelper()).subscribe(new io.reactivex.Observer<WanAndroidResponse<List<BannerBean>>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse) {
//                aa.tv.set("hehe");
//                view.setText("haha");
//                List<BannerBean> data = listWanAndroidResponse.getData();
//                if(data != null && data.size() > 0){
//                    ArrayList<String> imageUrls = new ArrayList<>();
//                    ArrayList<String> titles = new ArrayList<>();
//
//                    for(int i = 0; i < data.size();i ++){
//                        imageUrls.add(data.get(i).getImagePath());
//                        titles.add(data.get(i).getTitle());
//                    }
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
//                    //adapter.notifyDataSetChanged();
//
//                    view.setText("hh");
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

        final Banner banner = findViewById(R.id.banner);
        ApiHelper.getWanAndroidApis().getHomeBannerList().compose(RxUtils.<WanAndroidResponse<List<BannerBean>>>rxSchedulersHelper()).subscribe(new io.reactivex.Observer<WanAndroidResponse<List<BannerBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                
            }

            @Override
            public void onNext(WanAndroidResponse<List<BannerBean>> listWanAndroidResponse) {
                List<BannerBean> data = listWanAndroidResponse.getData();
                if(data != null && data.size() > 0){
                    ArrayList<String> imageUrls = new ArrayList<>();
                    ArrayList<String> titles = new ArrayList<>();

                    for(int i = 0; i < data.size();i ++){
                        imageUrls.add(data.get(i).getImagePath());
                        titles.add(data.get(i).getTitle());
                    }
                    //设置banner样式
                    banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages(imageUrls);
                    //banner设置方法全部调用完毕时最后调用
                    //设置banner动画效果
                    banner.setBannerAnimation(Transformer.DepthPage);
                    //设置标题集合（当banner样式有显示title时）
                    banner.setBannerTitles(titles);
                    //设置自动轮播，默认为true
                    banner.isAutoPlay(true);
                    //设置轮播时间
                    banner.setDelayTime(1500);
                    //设置指示器位置（当banner模式中有指示器时）
                    banner.setIndicatorGravity(BannerConfig.CENTER);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
       

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */


            //Glide 加载图片简单用法
            Glide.with(context).load((String)path).into(imageView);


        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }

}
