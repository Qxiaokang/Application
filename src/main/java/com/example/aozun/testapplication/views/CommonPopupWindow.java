package com.example.aozun.testapplication.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.example.aozun.testapplication.R;

/**
 * Created by Admin on 2017/6/19.
 */
public class CommonPopupWindow  extends PopupWindow{
    private View contentView,popView,anchorView;
    private int width,height;
    private boolean focusable;
    private Context context;
    private LayoutInflater layoutInflater;
    public CommonPopupWindow(){

    }
    public CommonPopupWindow(Context context,View contentView, View anchorView,int width, int height, boolean focusable){
        this.context=context;
        this.contentView=contentView;
        this.width=width;
        this.height=height;
        this.focusable=focusable;
        this.anchorView=anchorView;
        initView();
    }

    private void initView(){
        setContentView(contentView);
        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setAnimationStyle(R.style.window_up);
        setFocusable(focusable);
        setTouchable(true);
    }
    public void showAsDropDown(){
        showAsDropDown(anchorView);
    }
    public void showAsDropDown(int xoff, int yoff){
        showAsDropDown(anchorView,xoff,yoff);
    }
    public void showAsDropDown(int xoff, int yoff, int gravity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            showAsDropDown(anchorView,xoff,yoff,gravity);
        }else {
            showAsDropDown(anchorView,xoff,yoff);
        }
    }
    public void showAtLocation(int gravity, int x, int y){
        showAtLocation(anchorView,gravity,x,y);
    }
}
