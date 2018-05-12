package com.done.player;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.done.player.receiver.NetWorkManager;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * WebView加载网页
 *
 * @author done
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar pBar;
    private ImageView back;
    private Handler mHandler = new Handler();


    @Override
    public int getLayoutResId() {
        return com.done.player.R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        super.initView();
        pBar = (ProgressBar) findViewById(com.done.player.R.id.pBar);
        mWebView = (WebView) findViewById(com.done.player.R.id.webView);
        back = (ImageView) findViewById(com.done.player.R.id.back);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    if (pBar.getVisibility() == View.INVISIBLE) {
                        pBar.setVisibility(View.VISIBLE);
                        pBar.setProgress(newProgress);
                    }
                } else {
                    pBar.setProgress(100);
                    pBar.setVisibility(View.GONE);

                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存:

        // 全屏显示
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        if (NetWorkManager.isNetworkAvailable(getApplicationContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能

        mWebView.loadUrl(url);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();//表示等待证书响应
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        setStatusBarColor(com.done.player.R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        String name = getIntent().getStringExtra("name");
        toolbar.setTitle(name);
        toolbar.setTitleColor(Color.WHITE);
        toolbar.setBgColor(getResources().getColor(com.done.player.R.color.colorYellow));
    }
}
