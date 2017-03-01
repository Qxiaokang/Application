package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by HHD-H-I-0369 on 2017/1/6.
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
        getScreenSize();//获取屏幕宽高
        applicationShared=getSharedPreferences("applicationshared",MODE_PRIVATE);
    }

    public void getScreenSize(){
        WindowManager wm= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenH=displayMetrics.heightPixels;
        screenW=displayMetrics.widthPixels;
        s_density=displayMetrics.density;
        s_dendityDpi=displayMetrics.densityDpi;
        //screenW = getResources().getDisplayMetrics().widthPixels;
        //screenH = getResources().getDisplayMetrics().heightPixels;

    }
}
