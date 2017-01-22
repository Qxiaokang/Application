package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by HHD-H-I-0369 on 2017/1/6.
 */
public class BaseActivity extends Activity{
    protected int screenW ,screenH;
    protected SharedPreferences applicationShared;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
        getScreenSize();//获取屏幕宽高
        applicationShared=getSharedPreferences("applicationshared",MODE_PRIVATE);
    }

    public void getScreenSize(){
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
    }
}
