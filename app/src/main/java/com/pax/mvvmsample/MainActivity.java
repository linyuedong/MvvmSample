package com.pax.mvvmsample;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.Utils.CommonUtils;
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.RxUtils;
import com.example.library.Utils.ToastUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.noober.background.BackgroundLibrary;
import com.pax.mvvmsample.jetpack.MyLiveData;
import com.pax.mvvmsample.jetpack.MyModel;
import com.pax.mvvmsample.jetpack.MyObserver;
import com.pax.mvvmsample.ui.home.HomeActivity;
import com.squareup.haha.perflib.Main;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import kotlin.Unit;


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
    boolean isCollection = false;
    private void testDetail() {
        TextView tvComment = findViewById(R.id.tvComment);
        ImageView ivMessage = findViewById(R.id.ivMessage);
        ImageView ivCollection = findViewById(R.id.iv_collection);
        ImageView ivLike = findViewById(R.id.iv_like);
        ImageView ivSend = findViewById(R.id.iv_send);


        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_toast_collection, null);
        TextView tvToastCollection = view.findViewById(R.id.tvToastCollection);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);

        RxView.clicks(ivCollection).throttleFirst(200, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Exception {
                        if(isCollection){
                            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                            View view1 = ToastUtils.showCustomShort(R.layout.view_toast_collection);
                            ((TextView)view1.findViewById(R.id.tvToastCollection)).setText("取消收藏");
                            ivCollection.setImageResource(R.drawable.ic_collection3);
                            isCollection = false;
                        }else{
                            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                            View view1 = ToastUtils.showCustomShort(R.layout.view_toast_collection);
                            ((TextView)view1.findViewById(R.id.tvToastCollection)).setText("收藏成功");
                            ivCollection.setImageResource(R.drawable.ic_collection4);
                            isCollection = true;

                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe();




    }



}
