package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.aozun.testapplication.MainActivity;
import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.service.LockService;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.views.GestureLockViewGroup;
/**
 * 手势锁页面
*
* */
public class LockActivity extends BaseActivity{
    private GestureLockViewGroup lockViewGroup;
    private boolean lock=true;
    private Intent intent;
    private boolean islogin=true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lock);
        int pwdtime = applicationShared.getInt("pwdtimes", 0);
        LogUtils.d("pwdtime:"+ pwdtime);
        intent=new Intent(this, LockService.class);
        if(pwdtime==0){
            islogin=true;
        }else {
            islogin=false;
            stopService(intent);//进入锁屏后关闭锁屏监听
        }
        initViews();
    }

    private void initViews(){
        lockViewGroup = (GestureLockViewGroup) findViewById(R.id.GestureLockViewGroup_id);
        //lockViewGroup.setAnswer(new int[]{5, 10, 15, 20, 25,19,13,7,1,6,11,16,21,17,9,14,18,12,8,4,3,2});//设置密码
        //lockViewGroup.setAnswer(new int[]{5, 10, 15, 20, 25,19,13,7,1,6,11,16,21});//设置密码
        lockViewGroup.setAnswer(new int[]{5,4,3,2,1});//设置密码
        lockViewGroup.setUnMatchExceedBoundary(5);//设置最大尝试次数
        lockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener(){
            @Override
            public void onBlockSelected(int cId){

            }

            @Override
            public void onGestureEvent(boolean matched){
                if(matched){
                    Toast.makeText(LockActivity.this, "密码验证成功", Toast.LENGTH_LONG).show();
                    lock=false;
                    LockActivity.this.startService(intent);
                    //第一次登录的lock
                    if(islogin){
                        intent=new Intent(LockActivity.this, MainActivity.class);
                        LockActivity.this.startActivity(intent);
                        LockActivity.this.finish();
                    }
                    //不是登录的lock,直接finish
                    else{
                        LockActivity.this.finish();
                    }

                }else{
                    Toast.makeText(LockActivity.this, "密码验证失败，请重新验证", Toast.LENGTH_LONG).show();
                    lock=true;
                }
            }

            @Override
            public void onUnmatchedExceedBoundary(){
                Toast.makeText(LockActivity.this, "错误五次，请稍后再试！", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed(){
        LogUtils.i("lockactivity---onBackPressed");
        if(!lock||(lock&&islogin)){
           super.onBackPressed();
        }else {
            Toast.makeText(LockActivity.this,"请验证密码",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy(){
        LogUtils.e("lock_destroy");
        //不是登录，正常关闭时保存pwdtimes
        if(!islogin&&!lock){
            applicationShared.edit().putInt("pwdtimes",1).commit();
        }
        super.onDestroy();
    }


    @Override
    protected void onStop(){
        LogUtils.d("lock_onStop");
        //防止强制退出，登录状态未更改
        if(applicationShared.getInt("pwdtimes",0)==1&&!islogin){
            applicationShared.edit().putInt("pwdtimes",0).commit();
        }
        super.onStop();
    }
}
