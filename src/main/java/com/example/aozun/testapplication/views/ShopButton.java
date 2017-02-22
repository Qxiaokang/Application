package com.example.aozun.testapplication.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by HHD-H-I-0369 on 2017/2/21.
 * 购物按钮
 */
public class ShopButton extends Button{
    private Paint paint = new Paint();//初始化画笔
    private int btwidth = 100;//按钮的宽度
    private int btheight = 30;//按钮的高度
    private int time = 1000;//变化时间
    private boolean isOnclick = false;//是否已点击
    private boolean iscircle = false;//绘园
    private int colorback = Color.RED;//背景颜色
    private int circleR = 20, circleW = 4;//圆的半径,线宽
    private ValueAnimator aFloat, bFloat, cFloat,dFloat;
    private String text;
    private float aFloatf = 0, bFloatf = 0, cFloatf,dFloatf=0;
    private Path path = new Path();
    private int mcount = 0;
    private int btflag_l=0,btflag_r=0;

    public ShopButton(Context context){
        super(context);
    }

    public ShopButton(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public ShopButton(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        btwidth = MeasureSpec.getSize(widthMeasureSpec);
        btheight = MeasureSpec.getSize(heightMeasureSpec);
        circleR = btheight / 2 - circleW;
        setMeasuredDimension(btwidth, btheight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        RectF rect = new RectF(aFloatf * btwidth, 0, btwidth, btheight);
        if((iscircle && !isOnclick && mcount == 0) || (!iscircle && !isOnclick && mcount == 1) || !iscircle && !isOnclick && mcount == 0){
            //绘制文字背景颜色
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(colorback);
            paint.setAntiAlias(true);
            paint.setAlpha((int) (255 * (1 - aFloatf)));
            canvas.drawRoundRect(rect, 10, 10, paint);
            //绘制文字
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setTextSize(55);
            //设置文字居中
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAlpha((int) (255 * (1 - aFloatf)));
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int baseline = (int) ((rect.top + rect.bottom - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
            canvas.drawText(text + "", rect.centerX(), baseline, paint);
        }
        //渐隐
        aFloat = ValueAnimator.ofFloat(0, 1);
        aFloat.setDuration(time);
        aFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                aFloatf = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        aFloat.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
               setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                bFloat.start();
                iscircle = true;
                isOnclick = true;
                mcount = 1;
                setMcount(mcount);
                LogUtils.d("animation-a---end");
                setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
        //渐显
        cFloat = ValueAnimator.ofFloat(1, 0);
        cFloat.setDuration(time);
        cFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                cFloatf = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        //显示
        bFloat = ValueAnimator.ofFloat(1, 0);
        bFloat.setDuration(time);
        bFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                bFloatf = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        //渐变的时候不允许点击
        bFloat.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });
        //绘制圆
        if(aFloatf == 1){
            if(iscircle){
                LogUtils.i("mcount:" + mcount);
                if(mcount > 0){
                    //右圆
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(circleW);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setDither(true);
                    paint.setAntiAlias(true);
                    path.reset();
                    path.addCircle(btwidth - circleR - circleW, btheight / 2, circleR, Path.Direction.CW);
                    //右+
                    paint.setColor(Color.RED);
                    if(btflag_r==1){
                        paint.setColor(Color.GREEN);//点击更改+的颜色
                    }
                    paint.setAlpha((int) (255 * (1 - bFloatf)));
                    canvas.drawLine(btwidth - circleR / 2 - circleW, btheight / 2, btwidth - circleR / 2 * 3 - circleW, btheight / 2, paint);
                    canvas.drawLine(btwidth - circleR - circleW, circleR / 2 + circleW, btwidth - circleR - circleW, btheight - circleR / 2 - circleW, paint);
                    //左圆
                    paint.setColor(Color.BLACK);
                    path.addCircle((btwidth - (circleR + circleW) * 2) * bFloatf + circleR + circleW, btheight / 2, circleR, Path.Direction.CW);
                    paint.setAlpha((int) (255 * (1 - bFloatf)));
                    canvas.drawPath(path, paint);
                    canvas.save();
                    paint.setAlpha((int) (255 * (1 - bFloatf)));

                    if(btflag_l==1){
                        paint.setColor(Color.GREEN);//点击更改-颜色
                    }
                    canvas.rotate(720 * (bFloatf-1), (btwidth - (circleR + circleW) * 2) * bFloatf + circleR + circleW, btheight / 2);
                    //左-
                    canvas.drawLine(circleW + circleR / 2 + (btwidth - (circleW + circleR) * 2) * bFloatf, btheight / 2, circleW + circleR / 2 * 3 + (btwidth - (circleW + circleR) * 2) * bFloatf, btheight / 2, paint);
                    canvas.restore();

                    paint.setColor(Color.GREEN);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(40);
                    paint.setAlpha((int) (255 * (1 - bFloatf)));
                    canvas.save();
                    canvas.rotate(720 * (bFloatf-1), btwidth / 2 + bFloatf * (btwidth / 2 - circleW - circleR), btheight / 4 * 3 - paint.measureText(mcount + "") / 2);
                    canvas.drawText(mcount + "", btwidth / 2 + bFloatf * (btwidth / 2 - circleW - circleR), btheight / 4 * 3, paint);
                    canvas.restore();
                }else{
                    //数量减到0时绘制圆，复位
                    //右圆
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(circleW);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setDither(true);
                    paint.setAntiAlias(true);
                    path.reset();
                    path.addCircle(btwidth - circleR - circleW, btheight / 2, circleR, Path.Direction.CW);
                    //右+
                    paint.setColor(Color.RED);
                    paint.setAlpha((int) (255 * (1 - dFloatf)));
                    canvas.drawLine(btwidth - circleR / 2 - circleW, btheight / 2, btwidth - circleR / 2 * 3 - circleW, btheight / 2, paint);
                    canvas.drawLine(btwidth - circleR - circleW, circleR / 2 + circleW, btwidth - circleR - circleW, btheight - circleR / 2 - circleW, paint);
                    //左圆
                    paint.setColor(Color.BLACK);
                    path.addCircle((btwidth - (circleR + circleW) * 2) * dFloatf + circleR + circleW, btheight / 2, circleR, Path.Direction.CW);
                    paint.setAlpha((int) (255 * (1 - dFloatf)));
                    canvas.drawPath(path, paint);
                    canvas.save();
                    paint.setAlpha((int) (255 * (1 - dFloatf)));

                    canvas.rotate(720 * dFloatf, (btwidth - (circleR + circleW) * 2) * dFloatf + circleR + circleW, btheight / 2);
                    //左-
                    canvas.drawLine(circleW + circleR / 2 + (btwidth - (circleW + circleR) * 2) * dFloatf, btheight / 2, circleW + circleR / 2 * 3 + (btwidth - (circleW + circleR) * 2) * dFloatf, btheight / 2, paint);
                    canvas.restore();

                    //圆绘制完开始绘制初始的按钮
                    if(dFloatf==1){
                        paint.setColor(Color.GREEN);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(40);
                        paint.setAlpha((int) (255 * (1 - dFloatf)));
                        canvas.save();
                        canvas.rotate(720 * dFloatf, btwidth / 2 + dFloatf * (btwidth / 2 - circleW - circleR), btheight / 4 * 3 - paint.measureText(mcount + "") / 2);
                        canvas.drawText(mcount + "", btwidth / 2 + dFloatf * (btwidth / 2 - circleW - circleR), btheight / 4 * 3, paint);
                        canvas.restore();


                        RectF rectf = new RectF(btwidth * cFloatf, 0, btwidth, btheight);
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(colorback);
                        paint.setAntiAlias(true);
                        paint.setAlpha((int) (255 * (1 - cFloatf)));
                        canvas.drawRoundRect(rectf, 10, 10, paint);
                        paint.setColor(Color.WHITE);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(2);
                        paint.setTextSize(55);
                        //设置文字居中
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setAlpha((int) (255 * (1 - cFloatf)));
                        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
                        int baseline = (int) ((rectf.top + rectf.bottom - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
                        canvas.drawText(text + "", rectf.centerX(), baseline, paint);
                    }
                }
            }
        }
        cFloat.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                iscircle = false;
                isOnclick = false;
                aFloatf = 0;//重置为0,循环
                setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });

        dFloat=ValueAnimator.ofFloat(0,1);
        dFloat.setDuration(time);
        dFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                dFloatf= (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        dFloat.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator){
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator){
                cFloat.start();
            }

            @Override
            public void onAnimationCancel(Animator animator){
            }

            @Override
            public void onAnimationRepeat(Animator animator){
            }
        });

    }

    public void setShopText(String text){
        this.text = text;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(isOnclick&&iscircle){
                    //位置在左
                    if(x <= (circleR + circleW) * 2){
                       btflag_l=1;
                        invalidate();
                    }
                    //位置在右
                    if(x >= btwidth - (circleR + circleW) * 2){
                        btflag_r=1;
                        invalidate();
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("坐标x:" + x + "坐标Y:" + y);
                LogUtils.e("width:" + btwidth + "  height:" + btheight);
                if(!isOnclick){
                    iscircle = true;
                    aFloat.start();
                }else{
                    //位置在左
                    if(x <= (circleR + circleW) * 2){
                        btflag_l=0;
                        setMcount(mcount -= 1);
                        if(mcount == 0){
                            dFloat.start();
                        }else{
                            invalidate();
                        }
                    }
                    //位置在右
                    if(x >= btwidth - (circleR + circleW) * 2){
                        btflag_r=0;
                        setMcount(mcount += 1);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setMcount(int count){
        this.mcount = count;
    }

    public int getMcount(){
        return mcount;
    }
}
