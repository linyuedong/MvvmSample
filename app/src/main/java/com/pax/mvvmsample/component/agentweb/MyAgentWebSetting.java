package com.pax.mvvmsample.component.agentweb;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;

public class MyAgentWebSetting extends AbsAgentWebSettings {

    @Override
    public IAgentWebSettings toSetting(WebView webView) {
        IAgentWebSettings iAgentWebSettings = super.toSetting(webView);
        //AbsAgentWebSettings无法自适应屏幕大小以及缩放
        WebSettings ws = webView.getSettings();
        //自适应屏幕大小
        ws.setBuiltInZoomControls(true);
        //取消右下方缩放按钮
        ws.setDisplayZoomControls(false);
        //取消屏幕下方缩放滚动条
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // 可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        return iAgentWebSettings;


    }

    @Override
    protected void bindAgentWebSupport(AgentWeb agentWeb) {

    }
}
