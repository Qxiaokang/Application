package com.example.aozun.testapplication.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.BookSearchResponse;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.RetrofitUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/1/5.
 * retrofits网络请求
 */
public class Retrofit2Activity extends BaseActivity implements View.OnClickListener{
    private  List<BookSearchResponse.BooksBean> books=new ArrayList<BookSearchResponse.BooksBean>();
    private LinearLayout linearLayout;
    private Button start_bt;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        initViews();

    }

    private void initViews(){
        start_bt= (Button) findViewById(R.id.download_book);
        start_bt.setOnClickListener(this);
    }


    //显示books相关信息
    private void initImageloader(){
        linearLayout= (LinearLayout) findViewById(R.id.retrofit);
        linearLayout.removeAllViews();//移除所有，重新添加
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(400,400);
        LogUtils.d("books.size:"+books.size());
        for(int i = 0; i < books.size(); i++){
            layoutParams.topMargin=50;
            ImageView imageView=new ImageView(this);
            TextView textView=new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLUE);
            textView.setText("作者："+books.get(i).getAuthor().get(0)+"   价格："+books.get(i).getPrice().replace("元","")+"元"+"   出版社："+books.get(i).getPublisher());
            layoutParams.gravity= Gravity.CENTER;

            TextView tv=new TextView(this);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);
            tv.setText("  作者简介："+books.get(i).getAuthor_intro()+"\n\n\n  概要："+books.get(i).getSummary());
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            initImageloaders();
            ImageLoader.getInstance().displayImage(books.get(i).getImage(),imageView,getOptions());
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(tv);
            if(dialog!=null&&dialog.isShowing()){
                dialog.dismiss();
            }

        }
    }

    private void intRetrofit(){
        RetrofitUtil instance = RetrofitUtil.getInstance();
        instance.initRetrofit("https://api.douban.com/v2/","小王子","",0,0);
        //下载回调
        instance.setBody(new RetrofitUtil.BodyInterface(){
            @Override
            public BookSearchResponse getBody(BookSearchResponse body){
                List<BookSearchResponse.BooksBean> book = body.getBooks();
                books.clear();
                books.addAll(book);
                initImageloader();
                return body;
            }
        });
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.download_book:
                dialog=new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在加载，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                intRetrofit();
            break;
        }
    }

    @Override
    protected void onDestroy(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
