package com.pax.mvvmsample.ui.gank.beauty.bigphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.Utils.LogUtlis;
import com.example.library.view.statusBar.StatusBarUtil;
import com.pax.mvvmsample.R;

import java.util.ArrayList;

public class BigPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private int position;
    private ArrayList<String> urls;
    private ViewPager vpPhoto;
    private Button btSource;
    private ImageView ivDownload;
    private ImageView ivSave;
    private TextView tvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        getIntentData();
        initView();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        urls = intent.getStringArrayListExtra("urls");
    }

    private void initView() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.black));
        tvHint = findViewById(R.id.tvHint);
        btSource = findViewById(R.id.btSource);
        ivDownload = findViewById(R.id.ivDownload);
        ivSave = findViewById(R.id.ivSave);
        vpPhoto = findViewById(R.id.vpPhoto);
        btSource.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        ivDownload.setOnClickListener(this);
        BigPhotoPagerAdapter samplePagerAdapter = new BigPhotoPagerAdapter(this, urls,position);
        vpPhoto.setAdapter(samplePagerAdapter);
        vpPhoto.setCurrentItem(position,true);
        vpPhoto.addOnPageChangeListener(new MyOnPageChangeListener());
        tvHint.setText(dealHint(position));
    }

    private CharSequence dealHint(int position) {
        return (position+1) + "/" + urls.size();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSource:
                break;
            case R.id.ivSave:
                break;
            case R.id.ivDownload:
                break;

        }
    }

     class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            LogUtlis.i("onPageScrolled " +i);

        }

        @Override
        public void onPageSelected(int position) {
            LogUtlis.i("onPageSelected " +position);
             tvHint.setText(dealHint(position));
        }

        @Override
        public void onPageScrollStateChanged(int position) {
            LogUtlis.i("onPageScrollStateChanged " +position);

        }
    }


}
