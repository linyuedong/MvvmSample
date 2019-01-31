package com.pax.mvvmsample.component.agentweb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.Utils.CommonUtils;
import com.example.library.Utils.LogUtlis;
import com.example.library.Utils.ToastUtils;
import com.example.library.view.statusBar.StatusBarUtil;
import com.jakewharton.rxbinding3.view.RxView;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.noober.background.BackgroundLibrary;
import com.pax.mvvmsample.R;
import com.pax.mvvmsample.component.sonic.SonicImpl;
import com.pax.mvvmsample.component.sonic.SonicJavaScriptInterface;
import com.sum.slike.BitmapProvider;
import com.sum.slike.SuperLikeLayout;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class AgentWebActivity extends AppCompatActivity {


    protected AgentWeb mAgentWeb;
    private FrameLayout mFrameLayout;
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private AlertDialog mAlertDialog;

    private SonicImpl mSonicImpl;
    private boolean isCollection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        getIntentData();
        // 1. 首先创建SonicImpl
        mSonicImpl = new SonicImpl(mUrl, this);
        // 2. 调用 onCreateSession
        mSonicImpl.onCreateSession();
        setContentView(R.layout.activity_agent_web);
        initView();
        //3. 创建AgentWeb ，注意创建AgentWeb的时候应该使用加入SonicWebViewClient中间件
        long p = System.currentTimeMillis();
        createAgent();
        //4. 注入 JavaScriptInterface
        mAgentWeb.getJsInterfaceHolder().addJavaObject("sonic", new SonicJavaScriptInterface(mSonicImpl.getSonicSessionClient(), new Intent().putExtra("loadUrlTime", System.currentTimeMillis())));
        //5. 最后绑定AgentWeb
        mSonicImpl.bindAgentWeb(mAgentWeb);
        long n = System.currentTimeMillis();
        LogUtlis.i("init used time:" + (n - p));


    }

    private void createAgent() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mFrameLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setAgentWebWebSettings(getSettings())
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(null);

    }

    private void initView() {
        mFrameLayout =  this.findViewById(R.id.container);
        mToolbar =  this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView =  this.findViewById(R.id.toolbar_title);
        ImageView ivCollection = findViewById(R.id.iv_collection);
        ImageView ivLike = findViewById(R.id.iv_like);

        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgentWebActivity.this.finish();
            }
        });
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorPrimaryDark), 0);

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

        SuperLikeLayout superLikeLayout = findViewById(R.id.super_like_layout);
        BitmapProvider.Provider provider = new BitmapProvider.Builder(this).build();
//        BitmapProvider.Provider provider = new BitmapProvider.Builder(this)
//                .setDrawableArray(new int[]{R.mipmap.emoji_1, R.mipmap.emoji_2, R.mipmap.emoji_3, R.mipmap.emoji_4, R.mipmap.emoji_5, R.mipmap.emoji_6,
//                        R.mipmap.emoji_7, R.mipmap.emoji_8, R.mipmap.emoji_9, R.mipmap.emoji_10, R.mipmap.emoji_11, R.mipmap.emoji_12, R.mipmap.emoji_13,
//                        R.mipmap.emoji_14, R.mipmap.emoji_15, R.mipmap.emoji_16, R.mipmap.emoji_17, R.mipmap.emoji_18, R.mipmap.emoji_19, R.mipmap.emoji_20})
//                .setNumberDrawableArray(new int[]{R.mipmap.multi_digg_num_0, R.mipmap.multi_digg_num_1, R.mipmap.multi_digg_num_2, R.mipmap.multi_digg_num_3,
//                        R.mipmap.multi_digg_num_4, R.mipmap.multi_digg_num_5, R.mipmap.multi_digg_num_6, R.mipmap.multi_digg_num_7,
//                        R.mipmap.multi_digg_num_8, R.mipmap.multi_digg_num_9})
//                .setLevelDrawableArray(new int[]{R.mipmap.multi_digg_word_level_1, R.mipmap.multi_digg_word_level_2, R.mipmap.multi_digg_word_level_3})
//                .build();
        superLikeLayout.setProvider(provider);
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = (int)(v.getX() + v.getWidth() / 2);
                int y = (int)(v.getY() + v.getHeight() / 2);
                LogUtlis.i("x = " + x + " y = " + y);
                superLikeLayout.launch(x, y);
            }
        });

    }

    public IAgentWebSettings getSettings() {
        return new MyAgentWebSetting();
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
            LogUtlis.i("onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtlis.i("title:"+title);
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }


    private String mUrl;
    private String mTitle;
    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   标题
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle) {
        Intent intent = new Intent(mContext, AgentWebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", mTitle == null ? "加载中..." : mTitle);
        mContext.startActivity(intent);
    }

    public static void loadUrl(Context mContext, String mUrl) {
        Intent intent = new Intent(mContext, AgentWebActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mTitle", "详情");
        mContext.startActivity(intent);
    }

    private void getIntentData() {
        mUrl = getIntent().getStringExtra("mUrl");
        mTitle = getIntent().getStringExtra("mTitle");
    }

}