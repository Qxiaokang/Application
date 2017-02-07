package com.example.aozun.testapplication.activity;

import android.os.Bundle;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.MainApplication;

/**
 * Created by HHD-H-I-0369 on 2017/1/16.
 */
public class DiscrollActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discroll);
        MainApplication.getInstance().addActivity(this);

    }
}
