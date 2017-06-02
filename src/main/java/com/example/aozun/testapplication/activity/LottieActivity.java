package com.example.aozun.testapplication.activity;

import android.content.Context;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.aozun.testapplication.R;

/**
 * Lottie动画页面
 * */

public class LottieActivity extends BaseActivity{
    private LottieAnimationView mAnimation,mAnimationMore,mAnimationCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        init();
    }

    private void init(){
        mAnimation= (LottieAnimationView) findViewById(R.id.av_lottie1);
        mAnimationMore= (LottieAnimationView) findViewById(R.id.av_lottie2);
        mAnimationCenter= (LottieAnimationView) findViewById(R.id.av_lottie3);
        mAnimation.setImageAssetsFolder("Images");
        mAnimationCenter.setImageAssetsFolder("Images");
        startPlay();
    }

    private void playByNme(Context context , String jsonName, final LottieAnimationView lav){
        LottieComposition.Factory.fromAssetFileName(context, jsonName, new OnCompositionLoadedListener(){
            @Override
            public void onCompositionLoaded(LottieComposition composition){
                lav.setComposition(composition);
                lav.resumeAnimation();
            }
        });
    }
    //开始
    private void startPlay(){
        //方式一
        playByNme(this,"lottiefiles.com - Beating Heart.json",mAnimation);
        playByNme(this,"lottiefiles.com - ATM.json",mAnimationCenter);
        //方式二
        mAnimationMore.setProgress(0f);
        mAnimationMore.playAnimation();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mAnimation.cancelAnimation();
        mAnimationMore.cancelAnimation();
        mAnimationCenter.cancelAnimation();
    }
}
