package com.example.aozun.testapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by HHD-H-I-0369 on 2016/11/16.
 */
public class Netutil{
    public static  boolean connetWork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() == null){
            return false;
        }else{
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
    }
}
