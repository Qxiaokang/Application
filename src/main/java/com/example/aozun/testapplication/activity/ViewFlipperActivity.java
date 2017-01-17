package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.aozun.testapplication.R;

/**
 * 垂直滚动的广告
 * ViewFlipper常用方法介绍
 * isFlipping： 判断View切换是否正在进行
 * <p>
 * setFilpInterval：设置View之间切换的时间间隔
 * <p>
 * startFlipping：开始View的切换，而且会循环进行
 * <p>
 * stopFlipping：停止View的切换
 * <p>
 * setOutAnimation：设置切换View的退出动画
 * <p>
 * setInAnimation：设置切换View的进入动画
 * <p>
 * showNext： 显示ViewFlipper里的下一个View
 * <p>
 * showPrevious：显示ViewFlipper里的上一个View
 */
public class ViewFlipperActivity extends BaseActivity{
    private ViewFlipper viewFlipper;
    private TextView tvContent,title_tv,tvContent1,title_tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        intiViews();
    }

    private void intiViews(){
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_id);
        View view = View.inflate(this, R.layout.viewflipper_item, null);
        viewFlipper.addView(view);

        View view1=View.inflate(this,R.layout.viewflipper_item,null);
        tvContent = (TextView) view1.findViewById(R.id.flipper_content_tv);
        tvContent1 = (TextView) view1.findViewById(R.id.flipper_content_tv1);
        tvContent1.setText("iphone 8 曝光， 史上最美！");
        title_tv= (TextView) view1.findViewById(R.id.title_item);
        title_tv.setText("惊喜");
        title_tv1= (TextView) view1.findViewById(R.id.title_item1);
        title_tv1.setText("惊喜");
        tvContent.setText("iphone 8 曝光， 史上最美！");
        viewFlipper.addView(view1);

        View view2=View.inflate(this,R.layout.viewflipper_item,null);
        tvContent = (TextView) view2.findViewById(R.id.flipper_content_tv);
        tvContent1 = (TextView) view2.findViewById(R.id.flipper_content_tv1);
        title_tv= (TextView) view2.findViewById(R.id.title_item);
        title_tv1= (TextView) view2.findViewById(R.id.title_item1);
        title_tv.setText("活动");
        title_tv1.setText("热卖");
        tvContent1.setText("比奥迪A7还贵，上市第一天卖了27万！");
        tvContent.setText("比奥迪A7还贵，上市第一天卖了27万！");
        viewFlipper.addView(view2);
    }
}
