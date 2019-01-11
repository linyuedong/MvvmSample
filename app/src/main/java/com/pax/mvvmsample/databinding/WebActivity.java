package com.pax.mvvmsample.databinding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pax.mvvmsample.R;

public class WebActivity extends AppCompatActivity {
    WebView mWebview;
    ProgressBar mProgressbar;
    private String url;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            desc = intent.getStringExtra("desc");

        }
        mWebview = findViewById(R.id.webview);
        mProgressbar = findViewById(R.id.progressbar);
        initWebView();
    }

    private void initWebView() {
        mWebview.loadUrl(url);//加载url

        WebChromeClient webChromeClient = new WebChromeClient();

        WebViewClient webViewClient = new WebViewClient();
    }
}
