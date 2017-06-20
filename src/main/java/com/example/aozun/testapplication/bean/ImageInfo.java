package com.example.aozun.testapplication.bean;

import java.util.List;

/**
 * Created by Admin on 2017/6/20.
 */
public class ImageInfo{

    /**
     * status : 1
     * data : []
     * msg : 成功
     */

    private int status;
    private String msg;
    private List<ImageMessage> data;

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public List<ImageMessage> getData(){
        return data;
    }

    public void setData(List<ImageMessage> data){
        this.data = data;
    }
}
