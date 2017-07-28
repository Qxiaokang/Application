package com.example.aozun.testapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by Admin on 2017/7/28.
 */
public class MTRelativelayout extends RelativeLayout{
    private float x,y,startX,startY;
    private int width,height;
    private LinearLayout linearLayout=null;
    public MTRelativelayout(Context context){
        super(context);
    }

    public MTRelativelayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public MTRelativelayout(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public MTRelativelayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        linearLayout= (LinearLayout) findViewById(R.id.layout_include);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        x=event.getX();
        y=event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX=event.getX();
                startY=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(startX>x&&startX-x>width/3){
                    LogUtils.e("startAnimation");
                    startMoveAnimation();
                    return false;
                }else {
                    LogUtils.e("noStart");
                    linearLayout.setVisibility(GONE);
                 return true;
                }
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return true;
    }
    private void startMoveAnimation(){
        AnimationSet aniset=new AnimationSet(true);
        TranslateAnimation translateAnimation=new TranslateAnimation(getChildAt(0).getRight()+linearLayout.getWidth(),getChildAt(0).getRight(),0,0);
        AlphaAnimation alp=new AlphaAnimation(0,1);
        alp.setDuration(2000);
        translateAnimation.setDuration(2000);
        aniset.addAnimation(translateAnimation);
        aniset.addAnimation(alp);
        linearLayout.setVisibility(VISIBLE);
        linearLayout.startAnimation(aniset);
    }
}
