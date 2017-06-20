package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.views.CommonPopupWindow;
import com.example.aozun.testapplication.views.PicText;

/**
 * Created by Admin on 2017/6/9.
 */
public class CustomViewActivity extends BaseActivity implements View.OnClickListener{
    private LayoutInflater inflater;
    private CommonPopupWindow commonPopupWindow;
    private View contentView, parentView;
    private PicText picText;
    private TextView tv_takepic,tv_choosepic,tv_cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        initViews();
    }

    private void initViews(){
        picText = (PicText) findViewById(R.id.zs_pt);
        contentView = inflater.from(this).inflate(R.layout.popwindow_up, null);
        tv_takepic= (TextView) contentView.findViewById(R.id.tv_take);
        tv_choosepic= (TextView) contentView.findViewById(R.id.tv_choose);
        tv_cancel= (TextView) contentView.findViewById(R.id.tv_cancle);
        tv_takepic.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_choosepic.setOnClickListener(this);
        parentView=findViewById(R.id.window_layout);
        commonPopupWindow = new CommonPopupWindow(this, contentView, parentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        picText.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int actionInt = event.getAction();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.zs_pt:
                commonPopupWindow.showAtLocation(Gravity.BOTTOM, 0, 0);
            break;
            case R.id.tv_cancle:
                if(commonPopupWindow!=null&&commonPopupWindow.isShowing()){
                    commonPopupWindow.dismiss();
                }
                break;
            case R.id.tv_choose:
                if(commonPopupWindow!=null&&commonPopupWindow.isShowing()){
                    commonPopupWindow.dismiss();
                }
                Intent intent=new Intent(this,ShowImageActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_take:

                break;
            default:
                break;
        }
    }

}
