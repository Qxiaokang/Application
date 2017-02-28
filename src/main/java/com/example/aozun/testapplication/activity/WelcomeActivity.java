package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.adapter.WelcomPageAdapter;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.MainApplication;
import com.example.aozun.testapplication.views.IndexView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/2/27.
 * 引导页面
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener{
    private ViewPager wpager;
    private WelcomPageAdapter wadapter;
    private int changeTime = 2000;//变化时间
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;
    private TranslateAnimation translateAnimation, translateAnimation1;
    private AnimationSet animationSet1, animationSet2, animationSet3, animationSet4;
    private RotateAnimation rotateAnimation;
    private ImageView imageView;
    private ViewPager.OnPageChangeListener pageChange;
    private int[] imgIds = {R.drawable.a18572685_165713123142_2, R.drawable.b01bc5256ab12356ac7256cb0c9b6e3, R.drawable.c018c9055bee9a36ac7253f36b83945, R.drawable.d10351378_112256731678_2};
    private List<ImageView> list = new ArrayList<>();
    private Button enter;
    private IndexView indexView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MainApplication.getInstance().addActivity(this);
        initVews();
    }

    //初始化控件
    private void initVews(){
        enter= (Button) findViewById(R.id.bt_enter);
        enter.setOnClickListener(this);
        indexView= (IndexView) findViewById(R.id.index_id);
        list.clear();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for(int i = 0; i < imgIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgIds[i]));
            list.add(imageView);
        }
        wpager = (ViewPager) findViewById(R.id.wel_pager);
        wadapter = new WelcomPageAdapter(getApplicationContext(), list);
        wpager.setAdapter(wadapter);
        initAnimation();
        initPage();
        //默认为第一个
        wpager.addOnPageChangeListener(pageChange);
        pageChange.onPageSelected(0);
    }

    private void initPage(){
        pageChange = new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            }

            //page改变
            @Override
            public void onPageSelected(int position){
                imageView = list.get(position);
                LogUtils.d("positon:" + position);
                indexView.setIndex(position);
                switch(position){
                    case 0:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setAnimation(animationSet1);
                        animationSet1.startNow();
                        enter.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setAnimation(animationSet2);
                        animationSet2.startNow();
                        enter.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setAnimation(animationSet3);
                        animationSet3.startNow();
                        enter.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setAnimation(animationSet4);
                        animationSet4.startNow();
                        enter.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        };
    }

    //初始化动画
    private void initAnimation(){
        animationSet1 = new AnimationSet(true);
        animationSet2 = new AnimationSet(true);
        animationSet3 = new AnimationSet(true);
        animationSet4 = new AnimationSet(true);
        scaleAnimation = new ScaleAnimation(0, 1, 0, 1, screenW / 2, screenH / 2);
        scaleAnimation.setDuration(changeTime);
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(changeTime);
        translateAnimation = new TranslateAnimation(0, 0, -screenH, 0);
        translateAnimation.setDuration(changeTime);
        translateAnimation1 = new TranslateAnimation(screenW, 0, screenH, 0);
        translateAnimation1.setDuration(changeTime);
        rotateAnimation = new RotateAnimation(0, 360, screenW / 2, screenH / 2);
        rotateAnimation.setDuration(changeTime);
        animationSet1.addAnimation(translateAnimation);
        animationSet1.addAnimation(alphaAnimation);
        animationSet2.addAnimation(scaleAnimation);
        animationSet2.addAnimation(alphaAnimation);
        animationSet3.addAnimation(translateAnimation1);
        animationSet3.addAnimation(alphaAnimation);
        animationSet4.addAnimation(rotateAnimation);
        animationSet4.addAnimation(alphaAnimation);
        animationSet4.addAnimation(scaleAnimation);
    }

    @Override
    public void onClick(View view){
        Intent intent=new Intent(WelcomeActivity.this,ViewsActivity.class);
        startActivity(intent);
        finish();
    }
}
