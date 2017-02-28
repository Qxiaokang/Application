package com.example.aozun.testapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.aozun.testapplication.R;

/**
 * Created by HHD-H-I-0369 on 2017/2/27.
 * 自定义小圆点
 */
public class IndexView extends View{
    private int index;
    private int count=4;
    private float raidus=15;//圆点半径

    private int defalutColor= Color.WHITE;//默认颜色

    private int circlePadding=40;//圆点间距

    private  int selectColor=Color.GREEN;//选中颜色

    private Paint paint=new Paint();
    private float cx,cy;

    public IndexView(Context context){
        super(context);
    }

    public IndexView(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        count=typedArray.getInt(R.styleable.IndexView_c_count,4);
        raidus=typedArray.getFloat(R.styleable.IndexView_c_radius,15.0f);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        cx=(getWidth()-raidus*2*count-circlePadding*(count-1))/2;
        cy=getHeight()/2;

        for(int i = 0; i < count; i++){
            if(i==index){
                paint.setColor(selectColor);
            }
            else {
                paint.setColor(defalutColor);

            }
            canvas.drawCircle(cx+raidus+(raidus*2+circlePadding)*i,cy,raidus,paint);
        }

    }

    public void setIndex(int i){
        index=i;
        invalidate();
    }

}
