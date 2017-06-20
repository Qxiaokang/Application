package com.example.aozun.testapplication.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.aozun.testapplication.MobileApplication;

/**
 * Created by Admin on 2017/6/9.
 */
public class ComUtil{
    private Context context;
    private static ComUtil comUtil;

    private static Context getContext() {
        return MobileApplication.getInstance().getContext();
    }
    private ComUtil(Context context){
        this.context=context;
    }
    public synchronized static ComUtil getInstance(Context context){
        if(comUtil==null){
            comUtil=new ComUtil(context);
        }
        return comUtil;
    }

    /**
     * dp-->px
     * @param dp
     * @return
     */
    public static int dip2Px(int dp) {
        float density = getResources(getContext()).getDisplayMetrics().density;
        System.out.println("density:" + density);
        int px = (int) (dp * density + .5f);
        return px;
    }

    /**
     * px-->dp
     * @param
     * @return
     */
    public static int px2Dp(int px) {
        // px/dp = density;
        float density = getResources(getContext()).getDisplayMetrics().density;
        System.out.println("density:" + density);
        int dp = (int) (px / density + .5f);
        return px;
    }

    /**得到resouce对象*/
    public static Resources getResources(Context context) {
        return context.getResources();
    }
}
