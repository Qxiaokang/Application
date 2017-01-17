package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;

/**
 * Created by HHD-H-I-0369 on 2017/1/6.
 */
public class BaseActivity extends Activity{
    private int screenW ,screenH;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState){
        super.onCreate(savedInstanceState, persistentState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
        getScreenSize();//获取屏幕宽高
    }

    public void getScreenSize(){
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
    }
}
