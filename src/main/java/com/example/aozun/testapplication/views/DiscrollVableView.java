package com.example.aozun.testapplication.views;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.aozun.testapplication.interfaces.DiscrollVable;

/**
 * Created by HHD-H-I-0369 on 2017/1/13.
 */
public class DiscrollVableView extends FrameLayout implements DiscrollVable{
    private int mWidth;
    private int mHeight;
    private boolean mDiscrollveAlpha;

    private ArgbEvaluator evaluator=new ArgbEvaluator();
    public DiscrollVableView(Context context){
        super(context);
    }

    public DiscrollVableView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public DiscrollVableView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    public void onDiscrollve(float ratio){
    }

    @Override
    public void onResetDiscrollve(){
    }


    public void setDiscrollveAlpha(boolean alpha){
        this.mDiscrollveAlpha=alpha;
    }
}
