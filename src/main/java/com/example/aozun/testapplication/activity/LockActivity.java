package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.views.GestureLockViewGroup;

public class LockActivity extends BaseActivity{
    private GestureLockViewGroup lockViewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lock);
        initViews();
    }

    private void initViews(){

        lockViewGroup= (GestureLockViewGroup) findViewById(R.id.GestureLockViewGroup_id);
        lockViewGroup.setAnswer(new int[]{1,2,3,4,5});
        lockViewGroup.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener(){
            @Override
            public void onBlockSelected(int cId){

            }

            @Override
            public void onGestureEvent(boolean matched){

            }

            @Override
            public void onUnmatchedExceedBoundary(){
                Toast.makeText(LockActivity.this,"错误五次，请稍后再试！",Toast.LENGTH_LONG).show();
                lockViewGroup.setUnMatchExceedBoundary(5);

            }
        });
    }
}
