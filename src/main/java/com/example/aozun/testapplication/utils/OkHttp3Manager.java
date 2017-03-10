package com.example.aozun.testapplication.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
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
    private String resultString;

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
    public void getAsynString(String url, final Ok3CallBack ok3CallBack){
        Request.Builder requstBuilder = new Request.Builder().url(url).get();
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
                }
                resultString = response.body().string();
                LogUtils.d("数据：" + resultString == null || "".equals(resultString) ? "请求数据为空" : resultString);
                if(ok3CallBack != null){
                    ok3CallBack.success(resultString);
                    LogUtils.d("请求成功！");
                }
            }
        });
    }

    //异步的post提交jsonString
    public void postAsyn(String url, String jsonString, final Ok3CallBack ok3CallBack){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, jsonString);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = client3.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                LogUtils.e("异步post请求失败！");
                if(ok3CallBack != null){
                    ok3CallBack.error(e.toString());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                LogUtils.d("异步post请求成功");
            }
        });
    }

    //异步post提交
    public void postAsyn(String url, Map<Object,Object> map, final Ok3CallBack ok3CallBack){
        FormBody.Builder builder=new FormBody.Builder();
        RequestBody requestBody=null;
        if(map!=null&&map.size()!=0){
            Set<Map.Entry<Object, Object>> entries = map.entrySet();
            Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();
            while(iterator.hasNext()){
                Map.Entry<Object, Object> next = iterator.next();
                String key=next.getKey().toString();
                String value=next.getValue().toString();
                builder.add(key,value);
            }
            requestBody=builder.build();
        }
        if(requestBody!=null){
            Request request=new Request.Builder().post(requestBody).build();
            Call call=client3.newCall(request);
            call.enqueue(new Callback(){
                @Override
                public void onFailure(Call call, IOException e){
                    if(ok3CallBack!=null){
                        ok3CallBack.error(e.toString());
                    }
                    LogUtils.e("异步post请求失败！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    LogUtils.d("异步post请求成功");
                }
            });

        }

    }
    //同步get
    public String getSyn(String url) throws IOException{
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client3.newCall(request);
        Response executeResponse = call.execute();
        resultString=executeResponse.body().string();
        return resultString;
    }


    //同步post方法
    public boolean postSyn(String url,String key,String value) throws IOException{
        RequestBody requestBody=new FormBody.Builder()
                .add(key,value)
                .build();
        Request request=new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client3.newCall(request);
        Response executeResponse = call.execute();
        if(executeResponse.isSuccessful()){
            return true;
        }
        return false;
    }




    public interface Ok3CallBack{
        //失败
        void error(String ee);

        //成功
        void success(String ss);
    }
}
