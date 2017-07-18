package com.example.aozun.testapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.ComUtil;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by Admin on 2017/7/12.
 */
public class CountDownView extends View{
    private static final int MOVE_UP = 1;
    private static final int MOVE_DOWN = -1;
    private int MAX_MINUTE = 60;
    private int MIN_MINUTE = 0, selectIntLeft = 0,lastselectIntLeft=0, selectIntRight = 0,lastselectIntRight=0;//default is zero
    private Paint mPaint = null;
    private int mWhidth, mHeight, itemHeight, itemWhidth;
    private float mTextSize = 30, otherTextSize = 25,mStartTextSize=30;
    private int mTextColor = Color.BLACK, otherTextColor = Color.BLACK,mStartTextColor=Color.BLACK;
    private int moveInt = 0,moveIntRight=0;//
    private int baseline, type = 1,lastType=0;
    float startX = 0, rememberX=0;
    float startY = 0, rememberY=0;
    private boolean isLeft =true,isStart=false;
    private CountDownTimer countDownTimer=null;

    public CountDownTimer getCountDownTimer(){
        return countDownTimer;
    }

    public CountDownView(Context context){
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        MAX_MINUTE = typedArray.getInt(R.styleable.CountDownView_max_minute, 60);
        MIN_MINUTE = typedArray.getInt(R.styleable.CountDownView_min_minute, 0);
        mTextSize = typedArray.getDimension(R.styleable.CountDownView_mTextSize, 15);
        mTextColor = typedArray.getColor(R.styleable.CountDownView_mTextColor, Color.BLACK);
        otherTextColor = typedArray.getColor(R.styleable.CountDownView_mTBTextColor, Color.BLACK);
        otherTextSize = typedArray.getDimension(R.styleable.CountDownView_mTBTextSize, 25);
        mStartTextSize=typedArray.getDimension(R.styleable.CountDownView_mStartTextSize,30);
        mStartTextColor=typedArray.getColor(R.styleable.CountDownView_mStartTextColor,Color.BLACK);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2f);
        mPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWhidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        itemHeight = mHeight / 3;
        itemWhidth = mWhidth / 5;
        setMeasuredDimension(mWhidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(isStart){
            drawStartText(canvas);
        }else {
            drawAllText(canvas);
        }
    }
    private void drawStartText(Canvas canvas){
        mPaint.setColor(mStartTextColor);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(ComUtil.dip2Px((int) mStartTextSize));
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(getSelectTextLeft(), itemWhidth, mHeight / 2 + baseline, mPaint);
        canvas.drawText(":",mWhidth/2,mHeight/2+baseline,mPaint);
        canvas.drawText(getSelectTextRight(),itemWhidth*4,mHeight / 2 + baseline,mPaint);
    }
    private void drawAllText(Canvas canvas){
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0,itemHeight,mWhidth,itemHeight*2,mPaint);
        //canvas.drawText("00",mPaint.measureText());
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(ComUtil.dip2Px((int) mTextSize));
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        mPaint.setColor(mTextColor);
        if(type == MOVE_UP){
            lastType=MOVE_UP;
            mPaint.setTextSize(moveInt == 0 ? ComUtil.dip2Px((int) mTextSize) : ComUtil.dip2Px((int) mTextSize) - moveInt / itemHeight * ComUtil.dip2Px((int) (mTextSize - otherTextSize)));
            canvas.drawText(getSelectTextLeft(), itemWhidth, mHeight / 2 + baseline - moveInt, mPaint);
            canvas.drawText(":",mWhidth/2,mHeight/2+baseline,mPaint);
            canvas.drawText(getSelectTextRight(),itemWhidth*4,mHeight / 2 + baseline - moveIntRight,mPaint);
            drawOtherText(canvas,selectIntLeft,selectIntRight,MOVE_UP);
        }else if(type==MOVE_DOWN){
            lastType=MOVE_DOWN;
            mPaint.setTextSize(moveInt == 0 ? ComUtil.dip2Px((int) mTextSize) : ComUtil.dip2Px((int) mTextSize) - moveInt / itemHeight * ComUtil.dip2Px((int) (mTextSize - otherTextSize)));
            canvas.drawText(getSelectTextLeft(), itemWhidth, mHeight / 2 + baseline + moveInt, mPaint);
            canvas.drawText(":",mWhidth/2,mHeight/2+baseline,mPaint);
            canvas.drawText(getSelectTextRight(),itemWhidth*4,mHeight / 2 + baseline + moveIntRight,mPaint);
            drawOtherText(canvas, selectIntLeft, selectIntRight,MOVE_DOWN);
        }
    }
    private void drawOtherText(Canvas canvas, int time,int timeRight, int type){
        mPaint.setAlpha(180);
        mPaint.setColor(otherTextColor);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        baseline = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        if(type == MOVE_UP){
                mPaint.setTextSize(ComUtil.dip2Px((int) otherTextSize));
                LogUtils.e("top:" + fontMetrics.top + "  bottom:" + fontMetrics.bottom);
                String upString = time == 0 ? MAX_MINUTE + "" : (time - 1 < 10 ? "0" + (time - 1) : time - 1 + "");
                canvas.drawText(upString, itemWhidth, itemHeight / 2 + baseline - moveInt, mPaint);
                mPaint.setTextSize(moveInt == 0 ? ComUtil.dip2Px((int) otherTextSize) : ComUtil.dip2Px((int) otherTextSize) + moveInt / itemHeight * ComUtil.dip2Px((int) (mTextSize - otherTextSize)));
                String downString = time == 60 ? "00" : (time + 1 < 10 ? "0" + (time + 1) : time + 1 + "");
                canvas.drawText(downString, itemWhidth, mHeight - itemHeight / 2 + baseline - moveInt, mPaint);
                mPaint.setTextSize(ComUtil.dip2Px((int) otherTextSize));
                int down_2 = Integer.parseInt(downString) + 1;
                String downString_2 = down_2 < 10 ? "0" + down_2 : down_2 + "";
                canvas.drawText(downString_2, itemWhidth, mHeight + itemHeight / 2 + baseline - moveInt, mPaint);

                mPaint.setTextSize(ComUtil.dip2Px((int) otherTextSize));
                LogUtils.e("top:" + fontMetrics.top + "  bottom:" + fontMetrics.bottom);
                String upString1 = timeRight == 0 ? MAX_MINUTE + "" : (timeRight - 1 < 10 ? "0" + (timeRight - 1) : timeRight - 1 + "");
                canvas.drawText(upString1, itemWhidth*4, itemHeight / 2 + baseline - moveIntRight, mPaint);
                String downString1 = timeRight == 60 ? "00" : (timeRight + 1 < 10 ? "0" + (timeRight + 1) : timeRight + 1 + "");
                canvas.drawText(downString1, itemWhidth*4, mHeight - itemHeight / 2 + baseline - moveIntRight, mPaint);
                int down_3 = Integer.parseInt(downString1) + 1;
                String downString_3 = down_3 < 10 ? "0" + down_3 : down_3 + "";
                canvas.drawText(downString_3, itemWhidth*4, mHeight + itemHeight / 2 + baseline - moveIntRight, mPaint);
        }
        if(type == MOVE_DOWN){
                mPaint.setTextSize(moveInt == 0 ? ComUtil.dip2Px((int) otherTextSize) : ComUtil.dip2Px((int) otherTextSize) + moveInt / itemHeight * ComUtil.dip2Px((int) (mTextSize - otherTextSize)));
                LogUtils.e("top:" + fontMetrics.top + "  bottom:" + fontMetrics.bottom);
                String upString = time == 0 ? MAX_MINUTE + "" : (time - 1 < 10 ? "0" + (time - 1) : time - 1 + "");
                canvas.drawText(upString, itemWhidth, itemHeight / 2 + baseline +moveInt, mPaint);
                mPaint.setTextSize(ComUtil.dip2Px((int) otherTextSize));
                String downString = time == MAX_MINUTE ? "00" : (time + 1 < 10 ? "0" + (time + 1) : time + 1 + "");
                canvas.drawText(downString, itemWhidth, mHeight - itemHeight / 2 + baseline + moveInt, mPaint);

                int up_2 = Integer.parseInt(upString) - 1;
                String up_2String = up_2 < 10 ? "0" + up_2 : up_2 + "";
                canvas.drawText(up_2String, itemWhidth, -itemHeight / 2 + baseline + moveInt, mPaint);


                mPaint.setTextSize(moveIntRight == 0 ? ComUtil.dip2Px((int) otherTextSize) : ComUtil.dip2Px((int) otherTextSize) + moveInt / itemHeight * ComUtil.dip2Px((int) (mTextSize - otherTextSize)));
                LogUtils.e("top:" + fontMetrics.top + "  bottom:" + fontMetrics.bottom);
                String upString1 = timeRight == 0 ? MAX_MINUTE + "" : (timeRight - 1 < 10 ? "0" + (timeRight - 1) : timeRight - 1 + "");
                canvas.drawText(upString1, itemWhidth*4, itemHeight / 2 + baseline +moveIntRight, mPaint);
                mPaint.setTextSize(ComUtil.dip2Px((int) otherTextSize));
                String downString1 = timeRight == MAX_MINUTE ? "00" : (timeRight + 1 < 10 ? "0" + (timeRight + 1) : timeRight + 1 + "");
                canvas.drawText(downString1, itemWhidth*4, mHeight - itemHeight / 2 + baseline + moveIntRight, mPaint);

                int up_3 = Integer.parseInt(upString1) - 1;
                String up_2String1 = up_3 < 10 ? "0" + up_3 : up_3 + "";
                canvas.drawText(up_2String1, itemWhidth*4, -itemHeight / 2 + baseline + moveIntRight, mPaint);

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                rememberX=event.getX();
                rememberY=event.getY();
                if(isLeft){
                    moveInt=0;
                }else {
                    moveIntRight=0;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("moveX:" + x + "  moveY:" + y + "startx:" + startX + "  starty：" + startY);
                if(startX > 0 && startY > 0 && startX < itemWhidth * 2 && startY < mHeight&&x>0&&y>0&&x<itemWhidth*2&&y<mHeight){
                    isLeft =true;
                    if(y < startY){
                        type = MOVE_UP;
                        if(lastType==MOVE_DOWN){
                            lastselectIntLeft=-1;
                        }
                        moveInt = (int) (startY - y < itemHeight ? startY - y : (startY - y) % itemHeight);
                        LogUtils.i("moveInt---up:" + moveInt+"   %:"+(startY-y)%itemHeight);
                        if(moveInt>0.5*itemHeight){
                            if(selectIntLeft == MAX_MINUTE){
                                selectIntLeft = MIN_MINUTE;
                            }else{
                                if(lastselectIntLeft==-1){
                                    selectIntLeft += 1;
                                    moveInt = 0;
                                    lastselectIntLeft=selectIntLeft;
                                    invalidate();
                                }break;
                            }
                        }else {
                            lastselectIntLeft=-1;
                        }
                    }else{
                        type = MOVE_DOWN;
                        if(lastType==MOVE_UP){
                            lastselectIntLeft=-1;
                        }
                        if(moveInt!=-1){
                            moveInt = (int) (y - startY < itemHeight ? y - startY : (int)(y - startY) % itemHeight);
                        }
                        LogUtils.i("moveInt---down:" + moveInt+"   %:"+(y-startY)%itemHeight+"  itemHeight:"+itemHeight);
                        if(moveInt >0.3*itemHeight){
                            if(selectIntLeft == MIN_MINUTE){
                                moveInt=0;                                selectIntLeft = MAX_MINUTE;

                            }else{
                                if(lastselectIntLeft==-1){
                                    selectIntLeft -= 1;
                                    moveInt=0;
                                    lastselectIntLeft=selectIntLeft;
                                    invalidate();
                                }
                                break;
                            }
                        }else
                        {
                            lastselectIntLeft=-1;
                        }
                    }
                    invalidate();
                    LogUtils.e("selectIntLetf:" + selectIntLeft);
                }
                if(startX > itemWhidth*3 && startY > 0 && startX < mWhidth && startY < mHeight&&x>itemWhidth*3&&y>0&&x<mWhidth&&y<mHeight){
                    isLeft =false;
                    if(y < startY){
                        type = MOVE_UP;
                        if(lastType==MOVE_DOWN){
                            lastselectIntRight=-1;
                        }
                        moveIntRight = (int) (startY - y < itemHeight ? startY - y : (startY - y) % itemHeight);
                        LogUtils.i("moveIntRight---up:" + moveIntRight+"   %:"+(startY-y)%itemHeight);
                        if(moveIntRight>0.5*itemHeight){
                            if(selectIntRight == MAX_MINUTE){
                                selectIntRight = MIN_MINUTE;
                            }else{
                                if(lastselectIntRight==-1){
                                    selectIntRight += 1;
                                    moveIntRight = 0;
                                    lastselectIntRight=selectIntRight;
                                    invalidate();
                                }break;
                            }
                        }else {
                            lastselectIntRight=-1;
                        }
                    }else{
                        type = MOVE_DOWN;
                        if(lastType==MOVE_UP){
                            lastselectIntRight=-1;
                        }
                        if(moveIntRight!=-1){
                            moveIntRight = (int) (y - startY < itemHeight ? y - startY : (int)(y - startY) % itemHeight);
                        }
                        LogUtils.i("moveInt---down:" + moveIntRight+"   %:"+(y-startY)%itemHeight+"  itemHeight:"+itemHeight);
                        if(moveIntRight >0.3*itemHeight){
                            if(selectIntRight == MIN_MINUTE){
                                selectIntRight = MAX_MINUTE;
                                moveInt=0;
                            }else{
                                if(lastselectIntRight==-1){
                                    selectIntRight -= 1;
                                    moveIntRight=0;
                                    lastselectIntRight=selectIntRight;
                                    invalidate();
                                }
                                break;
                            }
                        }else
                        {
                            lastselectIntRight=-1;
                        }
                    }
                    invalidate();
                    LogUtils.e("selectIntRight:" + selectIntRight);
                }

                break;
            default:
                break;
        }
        if(isStart){
            return false;
        }
        return true;
    }

    //reset text
    public void resetText(){
        isStart=false;
        moveInt=0;
        selectIntLeft=0;
        selectIntRight=0;
        stopCountDown();
        invalidate();

    }
    public void stopCountDown(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    //start conuntDown
    public void startCountDown(){
        final long time=selectIntLeft*60*1000+selectIntRight*1000;
       /* if(isStart){
            time=selectIntLeft*60*1000+selectIntRight*1000+1000;
        }*/
        isStart=true;
        LogUtils.e("time:"+time/1000);
        countDownTimer=new CountDownTimer(time,1000){
            @Override
            public void onTick(long l){
                if(l==time){
                    l-=1000;
                }
                LogUtils.i("start---time:"+l/1000);
                selectIntRight= (int) (l/1000%60);
                selectIntLeft= (int) (l/1000/60);
                LogUtils.i("selectiintRight:"+selectIntRight+   "   selectintLeft:"+selectIntLeft);
                invalidate();
            }
            @Override
            public void onFinish(){
                selectIntRight=0;
                selectIntLeft=0;
                invalidate();
                LogUtils.i("finish");
            }
        };
        countDownTimer.start();
    }

    private String getSelectTextLeft(){
        String str = null;
        if(selectIntLeft < 10){
            str = "0" + selectIntLeft;
        }else{
            str = selectIntLeft + "";
        }
        return str;
    }

    private String getSelectTextRight(){
        String str = null;
        if(selectIntRight < 10){
            str = "0" + selectIntRight;
        }else{
            str = selectIntRight + "";
        }
        return str;
    }


    public int getSelectIntRight(){
        return selectIntRight;
    }

    public void setSelectIntRight(int selectIntRight){
        this.selectIntRight = selectIntRight;
    }

    public int getSelectIntLeft(){
        return selectIntLeft;
    }

    public void setSelectIntLeft(int selectIntLeft){
        this.selectIntLeft = selectIntLeft;
    }
}
