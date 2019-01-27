package com.pax.mvvmsample.ui.gank.beauty.bigphoto;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.RxUtils;
import com.example.library.view.statusBar.StatusBarUtil;
import com.pax.mvvmsample.BuildConfig;
import com.pax.mvvmsample.MainActivity;
import com.pax.mvvmsample.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BigPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private int mFirstPosition;
    private int mCurrentPosition;
    private ArrayList<String> mUrls;
    private ViewPager vpPhoto;
    private Button btSource;
    private ImageView ivDownload;
    private ImageView ivSave;
    private TextView tvHint;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        verifyStoragePermissions(this);
        getIntentData();
        initView();

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
        ivSave = findViewById(R.id.ivSave);
        vpPhoto = findViewById(R.id.vpPhoto);
        btSource.setOnClickListener(this);
        ivSave.setOnClickListener(this);
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
                break;
            case R.id.ivSave:
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
                String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AAA";
                File appDir = new File(storePath);
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                String fileName = System.currentTimeMillis() + ".jpg";
                File targetfile = new File(appDir, fileName);
                copy(file,targetfile);
                emitter.onNext(targetfile);
            }
        }).compose(RxUtils.<File>rxSchedulersHelper())
                .as(RxUtils.<File>bindLifecycle(BigPhotoActivity.this))
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        String absolutePath = file.getAbsolutePath();
                        LogUtlis.i("absolutePath = " + absolutePath);
                        //MediaScannerConnection.scanFile(file.getAbsolutePath(), "image/jpeg");
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


    public static void verifyStoragePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }

    }


        //保存文件到指定路径
        public static boolean saveImageToGallery(Context context, Bitmap bmp) {
            // 首先保存图片
            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                //通过io流的方式来压缩保存图片
                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
                fos.close();

                //把文件插入到系统图库
                //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                if (isSuccess) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void shareImage() {
        new ShareTask(this).execute(mUrls.get(mCurrentPosition));
    }

    class ShareTask extends AsyncTask<String, Void, File> {
        private final Context context;

        public ShareTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            String url = params[0]; // should be easy to extend to share multiple images at once
            try {
                return Glide
                        .with(context)
                        .downloadOnly()
                        .load(url)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get() // needs to be called on background thread
                        ;
            } catch (Exception ex) {
                LogUtlis.w("Sharing " + url + " failed", ex);
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                return;
            }
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", result);
            share(uri);
        }

        private void share(Uri uri) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Shared image");
            intent.putExtra(Intent.EXTRA_TEXT, "Look what I found!");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, "Share image"));
        }
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
