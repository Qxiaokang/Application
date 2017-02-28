package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.UniversalUtils;
import com.example.aozun.testapplication.views.GifView;

/**
 * 动态图片页面
 */
public class GifActivity extends BaseActivity{
    private GifView gifView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        initViews();
    }

    private void initViews(){
        gifView= (GifView) findViewById(R.id.gif_img);
        gifView.setMovieResource(R.raw.star111);
    }

    public void test(View view){
        UniversalUtils.getInstance().showToast(this,"--test--");
    }
}
