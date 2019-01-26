package com.pax.mvvmsample.ui.gank.beauty.bigphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.library.view.statusBar.StatusBarUtil;
import com.pax.mvvmsample.R;

public class BigPhotoActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.black));

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        ImageView iv = findViewById(R.id.ivphoto);

        Glide.with(this).load(url).into(iv);

    }

}
