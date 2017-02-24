package com.example.aozun.testapplication.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by HHD-H-I-0369 on 2017/2/22.
 * 自定义的点击可旋转的按钮
 */
public class OpenMenuTextView extends TextView{
    private int tvwidth=50,tvheight=50;
    private int lineLength=40;
    private int circleR=50,circleLW=3;
    private Paint paint=new Paint();
    private int changetime;
    private boolean isAdd=true,isDel=false;//添加和删除的标识
    private int coloradd=Color.GREEN,colordel=Color.RED;//添加和删除时，肉圆的颜色
    private int colorlineadd=Color.BLUE,colorlinedel=Color.BLACK;
    private ValueAnimator valueAnimatoradd,valueAnimatordel;
    private float addF,delF;
    private Tvlistener tvlistener;
    public OpenMenuTextView(Context context){
        super(context);
        setClickable(true);
    }

    public OpenMenuTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        setClickable(true);

    }

    public OpenMenuTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        tvwidth=MeasureSpec.getSize(widthMeasureSpec);
        tvheight=MeasureSpec.getSize(heightMeasureSpec);
        //宽高中取小的值
        tvwidth=tvwidth>tvheight?tvheight:tvwidth;
        lineLength=tvwidth*2/3;
        circleR=tvwidth/2-circleLW;
        setMeasuredDimension(tvwidth,tvwidth);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(coloradd);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(circleLW);
        paint.setStyle(Paint.Style.FILL);
        //中间的实心圆
        if(isDel){
            paint.setColor(colordel);
        }
        canvas.drawCircle(tvwidth/2,tvwidth/2,circleR,paint);
        //外圆
        paint.setColor(colorlineadd);
        paint.setStyle(Paint.Style.STROKE);
        if(isDel){
            paint.setColor(colorlinedel);
        }
        canvas.drawCircle(tvwidth/2,tvwidth/2,circleR,paint);

        paint.setColor(colorlineadd);
        paint.setStyle(Paint.Style.STROKE);
        if(isDel){
            paint.setColor(colorlinedel);
        }
        canvas.save();
        if(isDel){
            canvas.rotate(45*(1-delF),tvwidth/2,tvwidth/2);//
        }
        if(isAdd){
            canvas.rotate(45*addF,tvwidth/2,tvwidth/2);
        }
        canvas.drawLine(tvwidth-circleR-circleLW-lineLength/2,tvwidth/2,tvwidth-circleLW-circleR+lineLength/2,tvwidth/2,paint);
        canvas.drawLine(tvwidth/2,tvwidth/2-lineLength/2,tvwidth/2,tvwidth/2+lineLength/2,paint);
        canvas.restore();

        valueAnimatoradd=ValueAnimator.ofFloat(0,1);
        valueAnimatoradd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                addF= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatoradd.setDuration(changetime);
        valueAnimatoradd.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                isDel=true;
                isAdd=false;
                setClickable(true);//防止重复点击
                delF=0;//重复点击需重置
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });


        valueAnimatordel=ValueAnimator.ofFloat(0,1);
        valueAnimatordel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                delF= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatordel.setDuration(changetime);
        valueAnimatordel.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                setClickable(true);
                isDel=false;
                isAdd=true;
                addF=0;//复位后重置
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
    }

    //添加的方法
    public void setAdd(boolean a){
        isAdd=true;
        invalidate();
    }
    //删除的方法
    public void setDel(boolean d){
        isDel=d;
        invalidate();
    }
    //设置时间
    public void setChangeTime(int time){
        this.changetime=time;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                tvlistener.setOnTvlistener(this);//up的时候进行回调
                if(x<tvwidth&&y<tvwidth){
                    if(isAdd&&!isDel){
                        valueAnimatoradd.start();
                    }
                    if(isDel&&!isAdd){
                        valueAnimatordel.start();
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    //外部获取添加
    public boolean getAdd(){
        return isAdd;
    }
    //外部获取删除
    public boolean getDel(){
        return isDel;
    }
    //回调接口
    public interface  Tvlistener{
        void setOnTvlistener(View view);
    }
    //点击方法
    public void setTvlistener(Tvlistener tvlistener){
        this.tvlistener=tvlistener;
    }
}
