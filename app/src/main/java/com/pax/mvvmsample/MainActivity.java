package com.pax.mvvmsample;



import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.Utils.LogUtlis;
import com.pax.mvvmsample.jetpack.MyLiveData;
import com.pax.mvvmsample.jetpack.MyModel;
import com.pax.mvvmsample.jetpack.MyObserver;
import com.pax.mvvmsample.ui.home.HomeActivity;

import java.io.IOException;

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

        test();

    }



    public void test(){
        Toast.makeText(this,"hh",Toast.LENGTH_SHORT).show();


    }


}
