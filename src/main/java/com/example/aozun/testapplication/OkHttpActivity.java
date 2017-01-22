package com.example.aozun.testapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.aozun.testapplication.activity.BaseActivity;
import com.example.aozun.testapplication.utils.OkHttpClientManager;
import com.squareup.picasso.Picasso;

/**
 * okhttp加载图片
 * picasso加载图片
 * glide加载图片
 */
public class OkHttpActivity extends BaseActivity implements View.OnClickListener{
    private Button otbut;
    private ImageView imageView,picasso_iv,glide_iv;
    private static final String url="http://img3.fengniao.com/travel/2_960/1850.jpg";
    private static final String picurl1="http://nuuneoi.com/uploads/source/playstore/cover.jpg";
    private static final String picurl2="https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=gif%E5%9B%BE&step_word=&hs=2&pn=2&spn=0&di=5356811902&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=407946030%2C1201660130&os=3869529541%2C680616572&simid=3329441505%2C133996416&adpicid=0&lpn=0&ln=1982&fr=&fmq=1484646413391_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F19%2F20150519210608_j4BLQ.thumb.700_0.gif&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B17tpwg2_z%26e3Bv54AzdH3Frj5rsjAzdH3F4ks52AzdH3Fnm09bm0b8AzdH3F1jpwtsAzdH3F&gsm=0&rpstart=0&rpnum=0";
    private ProgressDialog progressDialog;
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
        picasso_iv= (ImageView) findViewById(R.id.picasso_iv);
        glide_iv= (ImageView) findViewById(R.id.glide_iv);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.okhttpbut:
                showDialog();
                //okhttp加载图片
                OkHttpClientManager.getInstance()._displayImage(imageView,url,110);
                //picasso加载图片
                Picasso.with(OkHttpActivity.this)
                        .load(picurl1)
                        .config(Bitmap.Config.ARGB_8888)
                        .into(picasso_iv);
                //glide加载图片
                Glide.with(OkHttpActivity.this)
                        .load(picurl2)
                        .asGif()
                        .into(glide_iv);
                cancleDialog();
            break;
        }

    }
    //显示对话框
    private void showDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("");
        progressDialog.setTitle("提示");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    //取消
    private void cancleDialog(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
