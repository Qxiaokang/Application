package com.example.aozun.testapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
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
    private boolean isStartAnim=false;
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
                    if(!isStartAnim){
                        LogUtils.e("startAnimation");
                        startMoveAnimation();
                    }
                    return true;
                }else {
                    LogUtils.e("noStart");
                    linearLayout.setVisibility(GONE);
                 return true;
                }
            case MotionEvent.ACTION_UP:
                isStartAnim=false;
                break;
            default:
                break;
        }
        return true;
    }
    private void startMoveAnimation(){
        LogUtils.d("padding:"+getChildAt(0).getPaddingRight());
        TranslateAnimation translateAnimation=new TranslateAnimation(getChildAt(0).getRight(),getChildAt(0).getRight()-getChildAt(0).getWidth(),0,0);
        translateAnimation.setDuration(2000);
        linearLayout.setVisibility(VISIBLE);
        linearLayout.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){
                isStartAnim=true;
            }
            @Override
            public void onAnimationEnd(Animation animation){
            }
            @Override
            public void onAnimationRepeat(Animation animation){
            }
        });
    }
}
