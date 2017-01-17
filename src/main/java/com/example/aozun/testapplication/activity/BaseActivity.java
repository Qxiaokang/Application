package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by HHD-H-I-0369 on 2017/1/6.
 */
public class BaseActivity extends Activity{
    private int screenW ,screenH;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState){
        super.onCreate(savedInstanceState, persistentState);
        getScreenSize();
    }

    public void getScreenSize(){
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
    }
}
