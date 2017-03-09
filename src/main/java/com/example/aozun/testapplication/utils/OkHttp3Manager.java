package com.example.aozun.testapplication.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 2017/3/9.
 * okhttp3网络请求
 */
public class OkHttp3Manager{
    private static OkHttp3Manager okHttp3Manager;
    private OkHttpClient client3;
    private OkHttpClient.Builder builder;
    private String requestString;
    private OkHttp3Manager(){
        //设置超时间和缓存
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(20000, TimeUnit.SECONDS).readTimeout(60000, TimeUnit.SECONDS).writeTimeout(60000, TimeUnit.SECONDS).cache(new Cache(InitContent.getInstance().getCacheFileDir(), InitContent.getInstance().getCacheSize()));//设置缓存
        client3 = builder.build();
    }

    public static OkHttp3Manager getInstance(){
        if(okHttp3Manager == null){
            synchronized(OkHttp3Manager.class){
                if(okHttp3Manager == null){
                    okHttp3Manager = new OkHttp3Manager();
                }
            }
        }
        return okHttp3Manager;
    }

    //异步的get请求
    public void getAsynString(String url,final Ok3CallBack ok3CallBack){
        Request.Builder requstBuilder = new Request.Builder().url(url);
        requstBuilder.method("Get", null);//默认为get，可省略
        Request request = requstBuilder.build();
        Call call = client3.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e){

                ok3CallBack.error(e.toString());
                LogUtils.d("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(null != response.cacheResponse()){
                    LogUtils.d("cacheString:" + response.cacheResponse().toString());
                }else{

                    requestString = response.body().string();
                    LogUtils.d("数据："+requestString);
                    ok3CallBack.success(requestString);
                    LogUtils.d("请求成功！");
                }
            }
        });

    }

    public void postAsyn(String url){
        RequestBody requestBody=new FormBody.Builder()
                .add("size","10")
                .build();




    }








    public interface Ok3CallBack{
        //失败
        void error(String ee);
        //成功
        void success(String ss);
    }

}
