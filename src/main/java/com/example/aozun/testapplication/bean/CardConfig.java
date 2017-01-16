package com.example.aozun.testapplication.bean;

/**
 * Created by HHD-H-I-0369 on 2017/1/16.
 * 介绍：一些配置
 * 界面最多显示几个View
 * 每一级View之间的Scale差异、translationY等等
 */

import android.content.Context;
import android.util.TypedValue;

public class CardConfig {
    //屏幕上最多同时显示几个Item
    public static int MAX_SHOW_COUNT;

    //每一级Scale相差0.05f，translationY相差7dp左右
    public static float SCALE_GAP;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context) {
        MAX_SHOW_COUNT = 4;
        SCALE_GAP = 0.03f;
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
    }
}

