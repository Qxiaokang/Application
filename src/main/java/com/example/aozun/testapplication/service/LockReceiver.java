package com.example.aozun.testapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.aozun.testapplication.activity.LockActivity;

/**
 * Created by HHD-H-I-0369 on 2017/1/11.
 * 锁屏的广播
 */
public class LockReceiver extends BroadcastReceiver{
    private Context context;
    private LockReceiver lockReceiver;
    public LockReceiver(Context context){
        this.context=context;
    }

    //注册广播
    public void regist(String sction,LockReceiver receiver){
        lockReceiver=receiver;
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(sction);
        context.registerReceiver(lockReceiver,intentFilter);
    }

    //接收广播，页面跳转
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Intent in=new Intent();
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.setClass(context,LockActivity.class);
            context.startActivity(in);
        }

    }

    //解除注册
    public void setUnregist(){
        context.unregisterReceiver(lockReceiver);
    }
}
