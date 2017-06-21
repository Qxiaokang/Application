package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.aozun.testapplication.R;

public class WebActivity extends BaseActivity{
    private WebView webView;
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView(){
        webView= (WebView) findViewById(R.id.wv_id);
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//js mutually
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//
        webView.loadUrl("file:///android_asset/html/Login.html");
    }
}
