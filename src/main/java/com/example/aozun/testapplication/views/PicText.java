package com.example.aozun.testapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by Admin on 2017/6/9.
 */
public class PicText extends TextView{
    private int textColor;
    private float textsize;
    private String textStringT,textStringB;
    private int textdrawable;
    private Paint mPaint;
    private int mWidth,mHeight;
    private int viewPadding=50;
    private float scaleF=1,picWidth,mtx;
    public PicText(Context context){
        this(context,null);
    }
    public PicText(Context context, AttributeSet attrs){
        this(context, attrs,0);
    }
    public PicText(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.PicText);
        textdrawable=typedArray.getResourceId(R.styleable.PicText_pic,R.drawable.lintman1);
        textStringT=typedArray.getString(R.styleable.PicText_picText1);
        textStringB=typedArray.getString(R.styleable.PicText_picText2);
        textsize= typedArray.getDimension(R.styleable.PicText_picTextsize, (float) 20.0);
        textColor=typedArray.getColor(R.styleable.PicText_picTextColor, Color.GRAY);
        mPaint=new Paint(Paint.DITHER_FLAG|Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textsize);
        mPaint.setStyle(Paint.Style.STROKE);
        LogUtils.d("text1:"+textStringT+"   text2:"+textStringB);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mWidth=getMeasuredWidth();
        mHeight=getMeasuredHeight();
        picWidth= (float) ((mHeight-getPaddingTop()-getPaddingBottom())*2/3.0);
        drawPic(canvas);
        drawText(canvas);
    }
    private void drawText(Canvas canvas){
        canvas.drawText(textStringT,picWidth+getPaddingLeft()+viewPadding,getPaddingTop()+picWidth/2,mPaint);
        canvas.drawText(textStringB,picWidth+getPaddingLeft()+viewPadding,mHeight-getPaddingBottom()-picWidth/2,mPaint);
    }
    private void drawPic(Canvas canvas){
        Matrix matrix=new Matrix();
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),textdrawable);
        mtx=picWidth/bitmap.getHeight();
        matrix.setScale(mtx,mtx);
        LogUtils.d("缩放比例："+mtx);
        bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        canvas.drawBitmap(bitmap,getPaddingLeft(),getPaddingTop()+picWidth/4,mPaint);
        //textdrawable.setBounds(getPaddingLeft(),getPaddingTop()+picWidth/4,picWidth+getPaddingLeft(),mHeight-getPaddingBottom()-picWidth/4);
    }
}
