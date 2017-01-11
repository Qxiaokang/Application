package com.example.aozun.testapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by HHD-H-I-0369 on 2017/1/11.
 * 监听锁屏的服务
 */
public class LockService extends Service{
    private LockReceiver lockReceiver;
    //创建
    @Override
    public void onCreate(){
        LogUtils.d("lockService服务创建");
         lockReceiver=new LockReceiver(this);
        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    //启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        LogUtils.d("lockService服务开启");
        lockReceiver.regist(Intent.ACTION_SCREEN_OFF,lockReceiver);
        return super.onStartCommand(intent, flags, startId);
    }


    //销毁
    @Override
    public void onDestroy(){
        LogUtils.d("lockService服务销毁");
        lockReceiver.setUnregist();
        super.onDestroy();
    }
}
