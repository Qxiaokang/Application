package com.example.aozun.testapplication.utils;

import com.example.aozun.testapplication.bean.BookSearchResponse;
import com.example.aozun.testapplication.interfaces.Retrofit2Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HHD-H-I-0369 on 2017/1/5.
 * retrofit工具类
 */
public class RetrofitUtil{
    private Call<BookSearchResponse> call;
    private BodyInterface bodyInterface;
    private static RetrofitUtil retrofitUtil;
    private RetrofitUtil(){

    }

    public synchronized static RetrofitUtil getInstance(){
        if(retrofitUtil==null){
            synchronized(RetrofitUtil.class){
                if(retrofitUtil==null){
                    retrofitUtil=new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

    //创建一个Retrofit的示例，并完成相应的配置
    public void initRetrofit(String baseurl,String name,String tag,int start,int count){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        Retrofit2Interface retrofit2Interface = retrofit.create(Retrofit2Interface.class);
        //调用请求方法，并得到Call实例
        call = retrofit2Interface.getSearchBooks(name, tag, start, count);
        getResponse();
    }

    private void getResponse() {
        //BookSearchResponse bookSearchResponse=call.execute().body();//同步请求
        //异步请求
        call.enqueue(new Callback<BookSearchResponse>(){
            @Override
            public void onResponse(Call<BookSearchResponse> call, Response<BookSearchResponse> response){
                BookSearchResponse body = response.body();
                LogUtils.d("body:"+body.getBooks().get(0).getImage());
                bodyInterface.getBody(body);
            }

            @Override
            public void onFailure(Call<BookSearchResponse> call, Throwable t){
            }
        });
    }
    //回调接口
    public interface BodyInterface{
        BookSearchResponse getBody(BookSearchResponse body);
    }
    public BookSearchResponse setBody(BodyInterface bodyInterface){
        this.bodyInterface=bodyInterface;
        return null;
    }

}
