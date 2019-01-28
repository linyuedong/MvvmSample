package com.example.library.view.webView;

import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.library.Utils.NetworkUtils;
import com.example.library.Utils.Utils;

import java.io.File;

public class WebUtils {

    public static final String CACHE_PATH = Utils.getApp().getCacheDir().getAbsolutePath()+File.separator + "web-cache";
    public static final String USERAGENT_UC = " UCBrowser/11.6.4.950 ";
    public static final String USERAGENT_QQ_BROWSER = " MQQBrowser/8.0 ";

    public static WebSettings getCommonWebSetting(WebView webView){
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setSavePassword(false);
        if (NetworkUtils.isNetworkConnected()) {
            //根据cache-control获取数据。
            ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网，则从本地获取，即离线加载
            ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //适配5.0不允许http和https混合使用情况
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        ws.setTextZoom(100);
        ws.setDatabaseEnabled(true);
        ws.setAppCacheEnabled(true);
        ws.setLoadsImagesAutomatically(true);
        ws.setSupportMultipleWindows(false);
        // 是否阻塞加载网络图片  协议http or https
        ws.setBlockNetworkImage(false);
        // 允许加载本地文件html  file协议
        ws.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            ws.setAllowFileAccessFromFileURLs(false);
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            ws.setAllowUniversalAccessFromFileURLs(false);
        }
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        ws.setLoadWithOverviewMode(false);
        ws.setUseWideViewPort(true);
        webView.setInitialScale(100);
        ws.setDomStorageEnabled(true);
        ws.setNeedInitialFocus(true);
        ws.setDefaultTextEncodingName("utf-8");//设置编码格式
        ws.setDefaultFontSize(16);
        ws.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        ws.setGeolocationEnabled(true);

        //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
        ws.setGeolocationDatabasePath(CACHE_PATH);
        ws.setDatabasePath(CACHE_PATH);
        ws.setAppCachePath(CACHE_PATH);

        //缓存文件最大值
        ws.setAppCacheMaxSize(Long.MAX_VALUE);

        ws.setUserAgentString(ws
                .getUserAgentString()
                .concat(USERAGENT_UC)
        );
        return ws;
    }
}
