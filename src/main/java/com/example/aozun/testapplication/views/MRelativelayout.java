package com.example.aozun.testapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Admin on 2017/7/13.
 */
public class MRelativelayout extends RelativeLayout{
    public MRelativelayout(Context context){
        super(context);
    }

    public MRelativelayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MRelativelayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }
    //分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        return false;
    }
    //拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return true;
    }
}
