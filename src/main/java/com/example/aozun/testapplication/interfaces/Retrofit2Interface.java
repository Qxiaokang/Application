package com.example.aozun.testapplication.interfaces;

import com.example.aozun.testapplication.bean.BookSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HHD-H-I-0369 on 2017/1/5.
 * https://api.douban.com/v2/book/search?q=%E5%B0%8F%E7%8E%8B%E5%AD%90&tag=&start=0&count=3
 *
 */
public interface Retrofit2Interface {

    //@GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面
    @GET("book/search")
    Call<BookSearchResponse> getSearchBooks(@Query("q") String name,@Query("tag") String tag,
                                            @Query("start") int start,@Query("count") int count);
}
