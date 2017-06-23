package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.db.TestOpenHelp;
import com.example.aozun.testapplication.utils.InitContent;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class PersonageActivity extends BaseActivity{
    private Intent intent=null;
    private String name,sex,picurl,city,province,user_id;
    private android.support.v7.widget.Toolbar  toolbar;
    private ImageView userpic;
    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage);
        init();
    }

    private void init(){
        userpic= (ImageView) findViewById(R.id.iv_user_pic);
        userName= (TextView) findViewById(R.id.tv_user_name);
        intent=getIntent();
        name=intent.getStringExtra("nickname");
        sex=intent.getStringExtra("sex");
        picurl=intent.getStringExtra("picurl");
        city=intent.getStringExtra("city");
        province=intent.getStringExtra("province");
        user_id= InitContent.getInstance().getUserId();
        toolbar= (Toolbar) findViewById(R.id.tl_id);
        toolbar.setTitle("个人信息");
        toolbar.setBackgroundColor(Color.GREEN);
        toolbar.setTitleTextColor(Color.RED);
        if(name!=null&&!"".equals(name)){
            userName.setText(name);
        }
        if(user_id!=null&&!"".equals(user_id)){
            Map<String, Object> queryMap = TestOpenHelp.getInstance(PersonageActivity.this).queryMap("select * from t_user where user_name='" + user_id + "'");
            if(queryMap!=null&&queryMap.size()>0){
                name=queryMap.get("user_ch_name").toString();
                sex=Integer.parseInt(queryMap.get("user_sex").toString())==1?"男":"女";
                city=queryMap.get("user_city").toString();
                province=queryMap.get("user_province").toString();
            }
        }
        initImageView();
    }

    private void initImageView(){
            Picasso.with(PersonageActivity.this)
                    .load(picurl)
                    .placeholder(R.drawable.normal)
                    .error(R.drawable.normal)
                    .into(userpic);
        }
}
