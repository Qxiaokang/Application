package com.example.aozun.testapplication.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import org.litepal.exceptions.GlobalException;

/**
 * Created by Admin on 2017/7/11.
 */
public class AppApplication extends MultiDexApplication{
    private static Context mContext=null;
    public static    AppApplication mInstance=null;
    @Override
    public void onCreate(){
        super.onCreate();
        mInstance=this;
        mContext=this.getApplicationContext();
    }
    public static Context getContext() {
        if(mContext == null) {
            throw new GlobalException("Application context is null. Maybe you haven\'t configured your application name with \"org.litepal.LitePalApplication\" in your AndroidManifest.xml. Or you can write your own application class, but remember to extend LitePalApplication as parent class.");
        } else {
            return mContext;
        }
    }
    /*public void onLowMemory() {
        super.onLowMemory();
        mContext = this.getApplicationContext();
    }*/
    public static AppApplication getInstance() {
        return mInstance;
    }
}
