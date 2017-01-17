package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aozun.testapplication.R;
import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.PasswordType;

/**
 * Created by HHD-H-I-0369 on 2017/1/3.
 * 密码验证页
 */
public class PwdViewActivity extends BaseActivity implements GridPasswordView.OnPasswordChangedListener, View.OnClickListener{
    private GridPasswordView passwordView;
    private Button startVideo;
    private String pwdString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_layout);
        initViews();

    }

    private void initViews(){
        passwordView = (GridPasswordView) findViewById(R.id.pass_view);
        passwordView.setPasswordType(PasswordType.NUMBER);
        startVideo = (Button) findViewById(R.id.startVideo);
        passwordView.setOnPasswordChangedListener(this);
        startVideo.setOnClickListener(this);
    }

    @Override
    public void onTextChanged(String psw){
    }

    @Override
    public void onInputFinish(String psw){
        this.pwdString = psw;
    }

    @Override
    public void onClick(View view){
        Intent intent = null;
        switch(view.getId()){
            case R.id.startVideo:
                if(pwdString == null || pwdString.length() < 6){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                }else{
                    if("123457".equals(pwdString)){
                        Toast.makeText(this, "验证成功", Toast.LENGTH_LONG).show();
                        intent = new Intent(this, VideoActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "密码错误，请重新输入", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
