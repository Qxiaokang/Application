package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.views.GestureLockViewGroup;
/**
 * 手势锁页面
*
* */
public class LockActivity extends BaseActivity{
    private GestureLockViewGroup lockViewGroup;
    private boolean lock=true;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lock);
        initViews();
    }

    private void initViews(){
        lockViewGroup = (GestureLockViewGroup) findViewById(R.id.GestureLockViewGroup_id);
        lockViewGroup.setAnswer(new int[]{1, 2, 3, 4, 5,10,15,20,25});//设置密码
        lockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener(){
            @Override
            public void onBlockSelected(int cId){

            }

            @Override
            public void onGestureEvent(boolean matched){
                if(matched){
                    Toast.makeText(LockActivity.this, "密码验证成功", Toast.LENGTH_LONG).show();
                    lock=false;
                    LockActivity.this.finish();
                }else{
                    Toast.makeText(LockActivity.this, "密码验证失败，请重新验证", Toast.LENGTH_LONG).show();
                    lock=true;
                }
            }

            @Override
            public void onUnmatchedExceedBoundary(){
                Toast.makeText(LockActivity.this, "错误五次，请稍后再试！", Toast.LENGTH_LONG).show();
                lockViewGroup.setUnMatchExceedBoundary(5);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(!lock){
           super.onBackPressed();
        }else {
            Toast.makeText(LockActivity.this,"请验证密码",Toast.LENGTH_LONG).show();
        }
    }
}
