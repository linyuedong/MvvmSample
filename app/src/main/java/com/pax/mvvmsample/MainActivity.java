package com.pax.mvvmsample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.library.Utils.LogUtlis;

import com.github.naturs.logger.Logger;
import com.pax.mvvmsample.Utils.RxUtils;
import com.pax.mvvmsample.databinding.LoginActivity;
import com.pax.mvvmsample.http.ApiHelper;
import com.pax.mvvmsample.http.bean.ThemeListBean;
import com.pax.mvvmsample.jetpack.MyLiveData;
import com.pax.mvvmsample.jetpack.MyModel;
import com.pax.mvvmsample.jetpack.MyObserver;
import io.reactivex.disposables.Disposable;



public class MainActivity extends AppCompatActivity {

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
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

//        RetrofitHelper.getInstance().getZhihuApis().getDailyList()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<DailyListBean>() {
//                    @Override
//            public void accept(DailyListBean dailyListBean) throws Exception {
//                LogUtlis.d(new Gson().toJson(dailyListBean));
//            }
//        });

        Test.test();


//        RetrofitHelper.getInstance().getGankApis().getRandomGirl(1)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<GankHttpResponse<List<GankItemBean>>>() {
//                               @Override
//                               public void accept(GankHttpResponse<List<GankItemBean>> listGankHttpResponse) throws Exception {
//                                   LogUtlis.d(new Gson().toJson(listGankHttpResponse));
//                               }
//                           }
//                );
//
//        RetrofitHelper.getInstance().getZhihuApis().getDailyList()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//
//                .subscribe(new Consumer<DailyListBean>() {
//                    @Override
//                    public void accept(DailyListBean dailyListBean) throws Exception {
//                        LogUtlis.d(new Gson().toJson(dailyListBean));
//                    }
//                });


        ApiHelper.getZhihuApis().getThemeList()
                .compose(RxUtils.<ThemeListBean>rxSchedulersHelper())
                .compose(RxUtils.<ThemeListBean>rxErrorHelper())
                .subscribe(new io.reactivex.Observer<ThemeListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ThemeListBean themeListBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtlis.i(e.getMessage());



                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Log.d("aa","cc");

        LogUtlis.d("cc");


    }




}
