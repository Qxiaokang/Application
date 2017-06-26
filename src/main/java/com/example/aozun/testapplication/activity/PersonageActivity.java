package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.db.TestOpenHelp;
import com.example.aozun.testapplication.utils.ComUtil;
import com.example.aozun.testapplication.utils.InitContent;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class PersonageActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{
    private Intent intent = null;
    private String name, sex, picurl, city, province, user_id;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView userpic;
    private TextView userName, userSex, userProvince, userCity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage);
        init();
    }
    //init views and values
    private void init(){
        userSex = (TextView) findViewById(R.id.tv_user_sex);
        userProvince = (TextView) findViewById(R.id.tv_user_province);
        userCity = (TextView) findViewById(R.id.tv_user_city);
        userpic = (ImageView) findViewById(R.id.iv_user_pic);
        userName = (TextView) findViewById(R.id.tv_user_name);
        intent = getIntent();
        name = intent.getStringExtra("nickname");
        sex = intent.getStringExtra("sex");
        picurl = intent.getStringExtra("picurl");
        city = intent.getStringExtra("city");
        province = intent.getStringExtra("province");
        user_id = InitContent.getInstance().getUserId();
        toolbar = (Toolbar) findViewById(R.id.tl_id);
        toolbar.setBackgroundColor(Color.GREEN);
        toolbar.setTitleTextColor(Color.RED);
        if(name != null && !"".equals(name)){
            userName.setText(name);
        }
        if(user_id != null && !"".equals(user_id)){
            Map<String, Object> queryMap = TestOpenHelp.getInstance(PersonageActivity.this).queryMap("select * from t_user where user_name='" + user_id + "'");
            if(queryMap != null && queryMap.size() > 0){
                name = queryMap.get("user_ch_name").toString();
                sex = Integer.parseInt(queryMap.get("user_sex").toString()) == 1 ? "男" : "女";
                city = queryMap.get("user_city").toString();
                province = queryMap.get("user_province").toString();
            }
        }
        toolbar.setTitleMarginStart(100);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(this);
        initValues();
    }
    //set value to Views
    private void initValues(){
        Picasso.with(PersonageActivity.this).load(picurl).placeholder(R.drawable.normal).error(R.drawable.normal).into(userpic);
        if(sex != null){
            userSex.setText(sex);
        }
        if(province != null){
            userProvince.setText(province);
        }
        if(city != null){
            userCity.setText(city);
        }
        if(name != null){
            userName.setText(name);
        }
    }
    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_set:
                ComUtil.getInstance(this).showToast("setting...");
                break;
            case R.id.menu_cancel:

                break;
            case R.id.menu_enter:
                ComUtil.getInstance(this).showToast("enter...");
                break;
            default:
                break;
        }
        return true;
    }
}
