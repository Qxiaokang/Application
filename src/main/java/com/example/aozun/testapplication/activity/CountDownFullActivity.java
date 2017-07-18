package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.views.CountDownView;

public class CountDownFullActivity extends BaseActivity implements View.OnClickListener{
    private CountDownView countDownView=null;
    private Intent intent=null;
    private RelativeLayout relativeLayout=null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_full);
        init();
    }

    private void init(){
        intent=getIntent();
        countDownView= (CountDownView) findViewById(R.id.full_countdown);
        relativeLayout= (RelativeLayout) findViewById(R.id.ly_relative);
        countDownView.setSelectIntLeft(intent.getIntExtra("leftInt",0));
        countDownView.setSelectIntRight(intent.getIntExtra("rightInt",0));
        countDownView.startCountDown();
        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        LogUtils.e("FullScreen   onClick---");
        switch(view.getId()){
            case R.id.ly_relative:
                countDownView.stopCountDown();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        countDownView.stopCountDown();
        finish();
        LogUtils.e("onTouch-----");
        return super.onTouchEvent(event);
    }
}
