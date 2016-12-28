package com.example.aozun.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.aozun.testapplication.adapter.ImageAdapterSerial;
import com.example.aozun.testapplication.views.HrglView;

/**
 * Created by HHD-H-I-0369 on 2016/11/22.
 */
public class HrActivity extends Activity{
    private int images[]={R.mipmap.rongguangwen,R.mipmap.dg,R.mipmap.dsadads,R.mipmap.hfhh,R.mipmap.kiklhkjhkjk};
    private String [] Strings={"刘峰","赵开桢","","宋义魁","胡丹"};
    private HrglView hrglView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hractivity_layout);
        initViews();
    }

    private void initViews(){
        hrglView= (HrglView) findViewById(R.id.hrgid);
        ImageAdapterSerial adapterSerial=new ImageAdapterSerial(this,images,Strings,getResources().getDisplayMetrics().heightPixels/2);
        hrglView.setAdapter(adapterSerial);

    }
}
