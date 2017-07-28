package com.example.aozun.testapplication.bean;

import android.graphics.Bitmap;

/**
 * Created by Admin on 2017/7/24.
 */
public class Image{
    private Bitmap bitmap;
    private String path;
    private boolean isChecked=false;
    private int position;
    private boolean isUpload=false;
    private int type;
    private String createTime="";

    public String getCreateTime(){
        return createTime;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public boolean isUpload(){
        return isUpload;
    }

    public void setUpload(boolean upload){
        isUpload = upload;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }
}
