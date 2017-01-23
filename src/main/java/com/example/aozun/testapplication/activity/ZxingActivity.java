package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aozun.testapplication.R;

import zxing.MipcaActivityCapture;

public class ZxingActivity extends BaseActivity{
    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 显示扫描结果
     */
    private TextView mTextView;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        init();
    }

    private void init(){
        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
        //点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
        //扫描完了之后调到该界面
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(ZxingActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SCANNIN_GREQUEST_CODE:
                switch(resultCode){
                    case RESULT_OK:
                        Bundle bundle = data.getExtras();
                        if(bundle==null||(bundle!=null&&bundle.getString("result")==null)){
                            Toast.makeText(ZxingActivity.this,"解析失敗",Toast.LENGTH_LONG).show();
                        }else {
                            mTextView.setText(bundle.getString("result") + "");
                            //mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                        }
                        break;
                }
                break;
        }
    }
}
