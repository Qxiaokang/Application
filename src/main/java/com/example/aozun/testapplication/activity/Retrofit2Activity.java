package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class Retrofit2Activity extends BaseActivity{
    private  List<BookSearchResponse.BooksBean> books=new ArrayList<BookSearchResponse.BooksBean>();
    private LinearLayout linearLayout;
    private  BookSearchResponse bookSearchResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        intRetrofit();
       initImageloader();

    }

    private void initImageloader(){
        linearLayout= (LinearLayout) findViewById(R.id.retrofit);
        linearLayout.removeAllViews();//移除所有，重新添加
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LogUtils.d("books.size:"+books.size());
        for(int i = 0; i < books.size(); i++){
            ImageView imageView=new ImageView(this);
            layoutParams.setMargins(50,50,50,50);
            layoutParams.gravity= Gravity.CENTER;
            imageView.setLayoutParams(layoutParams);
            ImageLoader.getInstance().displayImage(books.get(i).getImage(),imageView,getOptions());
            linearLayout.addView(imageView);
        }



    }

    private void intRetrofit(){
        RetrofitUtil instance = RetrofitUtil.getInstance();
        instance.initRetrofit("https://api.douban.com/v2/","小王子","",0,0);
        bookSearchResponse= instance.setBody(new RetrofitUtil.BodyInterface(){
            @Override
            public BookSearchResponse getBody(BookSearchResponse body){
                List<BookSearchResponse.BooksBean> book = body.getBooks();
                books.addAll(book);

                return body;
            }
        });
    }
}
