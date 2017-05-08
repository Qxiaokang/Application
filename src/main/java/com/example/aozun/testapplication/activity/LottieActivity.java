package com.example.aozun.testapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.aozun.testapplication.R;

/**
 * Lottie动画页面
 * */

public class LottieActivity extends AppCompatActivity{
    private LottieAnimationView mAnimation,mAimationMore;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        init();
    }

    private void init(){
        mAnimation= (LottieAnimationView) findViewById(R.id.av_lottie1);
        mAimationMore= (LottieAnimationView) findViewById(R.id.av_lottie2);
        mAnimation.setImageAssetsFolder("Images");
        startPlay();
    }

    private void playByNme(Context context ,String jsonName){
        LottieComposition.Factory.fromAssetFileName(context, jsonName, new OnCompositionLoadedListener(){
            @Override
            public void onCompositionLoaded(LottieComposition composition){
                mAnimation.setComposition(composition);
                mAnimation.resumeAnimation();
            }
        });
    }
    //开始
    private void startPlay(){
        //方式一
        playByNme(this,"PinJump.json");
        //方式二
        mAimationMore.setProgress(0f);
        mAimationMore.playAnimation();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mAnimation.cancelAnimation();
        mAimationMore.cancelAnimation();
    }
}
