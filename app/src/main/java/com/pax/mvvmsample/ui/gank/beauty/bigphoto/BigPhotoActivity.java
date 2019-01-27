package com.pax.mvvmsample.ui.gank.beauty.bigphoto;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.library.Utils.FileUtils;
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.PermissionsUtils;
import com.example.library.Utils.RxUtils;
import com.example.library.Utils.ToastUtils;
import com.example.library.component.Constants;
import com.example.library.view.statusBar.StatusBarUtil;
import com.pax.mvvmsample.BuildConfig;
import com.pax.mvvmsample.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class BigPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private int mFirstPosition;
    private int mCurrentPosition;
    private ArrayList<String> mUrls;
    private ViewPager vpPhoto;
    private Button btSource;
    private ImageView ivDownload;
    private ImageView ivShare;
    private TextView tvHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        getIntentData();
        initView();
        initData();

    }

    private void initData() {
        requestPermission();
        mCurrentPosition = mFirstPosition;

    }

    private void requestPermission() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        LogUtlis.i("permission.granted");
                        // `permission.name` is granted !
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        LogUtlis.i("permission.shouldShowRequestPermissionRationale");
                        // Denied permission without ask never again
                    } else {
                        LogUtlis.i("Denied permission with ask never again");
                        // Denied permission with ask never again
                        // Need to go to the settings
                        PermissionsUtils.gotoPermission();
                    }
                });

    }

    private void getIntentData() {
        Intent intent = getIntent();
        mFirstPosition = intent.getIntExtra("position", 0);
        mUrls = intent.getStringArrayListExtra("urls");
    }

    private void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        tvHint = findViewById(R.id.tvHint);
        btSource = findViewById(R.id.btSource);
        ivDownload = findViewById(R.id.ivDownload);
        ivShare = findViewById(R.id.ivShare);
        vpPhoto = findViewById(R.id.vpPhoto);
        btSource.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivDownload.setOnClickListener(this);
        BigPhotoPagerAdapter samplePagerAdapter = new BigPhotoPagerAdapter(this, mUrls, mFirstPosition);
        vpPhoto.setAdapter(samplePagerAdapter);
        vpPhoto.setCurrentItem(mFirstPosition, true);
        vpPhoto.addOnPageChangeListener(new MyOnPageChangeListener());
        tvHint.setText(dealHint(mFirstPosition));
    }

    private CharSequence dealHint(int position) {
        return (position + 1) + "/" + mUrls.size();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSource:
                ToastUtils.showShort("抱歉，没有原图");
                break;
            case R.id.ivShare:
                shareImage();
                break;
            case R.id.ivDownload:
                downloadImage();
                break;

        }
    }

    private void downloadImage() {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File file = Glide
                        .with(getApplicationContext())
                        .downloadOnly()
                        .load(mUrls.get(mCurrentPosition))
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                emitter.onNext(file);
            }
        }).map(new Function<File, File>() {
            @Override
            public File apply(File file) throws Exception {
                File appDir = new File(Constants.EXSD_PATH + "AAA");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File targetfile = new File(appDir, fileName);
                FileUtils.copy(file,targetfile);
                return targetfile;
            }
        })
                .compose(RxUtils.<File>rxSchedulersHelper())
                .as(RxUtils.<File>bindLifecycle(BigPhotoActivity.this))
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        ToastUtils.showShort("保存成功");
                        Uri uri = Uri.fromFile(file);
                        getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    private void shareImage() {
        Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                File file = Glide
                        .with(getApplicationContext())
                        .downloadOnly()
                        .load(mUrls.get(mCurrentPosition))
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                emitter.onNext(file);
            }
        }).compose(RxUtils.<File>rxSchedulersHelper())
                .as(RxUtils.<File>bindLifecycle(BigPhotoActivity.this))
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                        share(uri);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void share(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Shared image");
        intent.putExtra(Intent.EXTRA_TEXT, "Look what I found!");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share image"));
    }

    class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
            LogUtlis.i("onPageScrolled " + i);

        }

        @Override
        public void onPageSelected(int position) {
            LogUtlis.i("onPageSelected " + position);
            mCurrentPosition = position;
            tvHint.setText(dealHint(position));
        }

        @Override
        public void onPageScrollStateChanged(int position) {
            LogUtlis.i("onPageScrollStateChanged " + position);

        }
    }


}
