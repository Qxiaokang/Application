package com.example.aozun.testapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.aozun.testapplication.utils.OkHttpClientManager;

/**
 * okhttp加载图片
 */
public class OkHttpActivity extends Activity implements View.OnClickListener{
    private Button otbut;
    private ImageView imageView;
    private static final String url="http://img3.fengniao.com/travel/2_960/1850.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ok_http);
        initViews();
    }

    private void initViews(){
        otbut= (Button) findViewById(R.id.okhttpbut);
        imageView= (ImageView) findViewById(R.id.imageid);
        otbut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.okhttpbut:
                OkHttpClientManager.getInstance()._displayImage(imageView,url,110);
            break;
        }

    }
}
