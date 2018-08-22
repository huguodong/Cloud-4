package com.ssitcloud.app_reader.view;

import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.RequestUrlUtil;
import com.ssitcloud.app_reader.databinding.ActivityWebSettingBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by LXP on 2017/8/11.
 * 用户网页设置界面
 */

public class WebSettingActivity extends BaseActivity {
    private ConfigBizI configBiz;
    private ActivityWebSettingBinding dataBinding;

    private WebView webView;
    private String mailUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_setting);
        configBiz = new ConfigBizImpl(this);
        webView = dataBinding.webView;
        init(webView);

        mailUrl = RequestUrlUtil.getUrl(getResources(), R.string.web_setting_url);
        String auth = configBiz.getAuthCode();
        if (auth == null) {
            logout();
            finish();
            return;
        }
        try {
            webView.loadUrl(mailUrl + "?reader_auth=" + URLEncoder.encode(auth, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logout();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
    }

    private void init(WebView webView) {
        webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                dataBinding.title.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                dataBinding.loadProgress.setProgress(newProgress);
                if(newProgress < 100){
                    dataBinding.loadProgress.setVisibility(View.VISIBLE);
                }else{
                    dataBinding.loadProgress.setVisibility(View.GONE);
                }
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    public void finish(View v) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
