package com.example.aozun.testapplication.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.views.GifView;

/**
 * 动态图片页面
 */
public class GifActivity extends BaseActivity implements View.OnClickListener{
    private GifView gifView;
    private AppCompatImageView compatImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        initViews();
    }
    //初始化gif图片，控件
    private void initViews(){
        gifView= (GifView) findViewById(R.id.gif_img);
        compatImageView= (AppCompatImageView) findViewById(R.id.bai_imag_id);
        compatImageView.setVisibility(View.INVISIBLE);
        gifView.setMovieResource(R.raw.star111);

    }
    //点击按钮手动调用imageview的监听
    public void test(View view){
        compatImageView.setVisibility(View.VISIBLE);
        onClick(compatImageView);
    }


    @Override
    public void onClick(View view){
        ImageView imageView= (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        LogUtils.e("onclick");
        if(drawable instanceof Animatable){
            LogUtils.e("start");
            ((Animatable) drawable).start();
        }
    }
}
