package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by HHD-H-I-0369 on 2017/1/6.
 * Activity的基类
 */
public class BaseActivity extends Activity{
    protected int screenW ,screenH;
    protected SharedPreferences applicationShared;
    //屏幕密度
    protected float s_density;
    protected int s_dendityDpi;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
        getScreenSize();
        applicationShared=getSharedPreferences("applicationshared",MODE_PRIVATE);
    }
    //获取屏幕的相关信息
    public void getScreenSize(){
        WindowManager wm= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenH=displayMetrics.heightPixels;
        screenW=displayMetrics.widthPixels;
        s_density=displayMetrics.density;//屏幕密度(0.75/1.0/1.5)
        s_dendityDpi=displayMetrics.densityDpi;//屏幕密度dpi(120/160/240)
        //screenW = getResources().getDisplayMetrics().widthPixels;
        //screenH = getResources().getDisplayMetrics().heightPixels;

    }
}
