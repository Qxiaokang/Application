package com.example.aozun.testapplication.utils;

import android.os.Environment;

import com.example.aozun.testapplication.MobileApplication;

import java.io.File;

/**
 * Created by HHD-H-I-0369 on 2017/3/2.
 * 初始化类
 */
public class InitContent{
    private static InitContent initContent;
    private String photoPath;
    private int cacheSize=10*1024*1024;
    private File fileDir;
    private String userId="";

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    private InitContent(){
    }

    public static synchronized InitContent getInstance(){
        if(initContent == null){
            initContent = new InitContent();
        }
        return initContent;
    }

    public String getPhotoPath(){
        boolean b=true;
        //sd卡挂载成功，返回路径
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            photoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "images" + File.separator + "takephotos";
            File file = new File(photoPath);
            if(!file.exists()){
                file.mkdirs();
            }else {
               //UniversalUtils.delFile(file);
            }
        }else{
            photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "images" + File.separator + "takephotos";
            File file = new File(photoPath);
            if(!file.exists()){
                b=file.mkdirs();
                LogUtils.i("---create---directory---boolean:"+b);
            }else {
                //UniversalUtils.delFile(file);
            }
            LogUtils.e("---sd---mounted--faild");
            if(!b){
                photoPath=MobileApplication.getInstance().getApplicationContext().getFilesDir()+File.separator+"images";
                file=new File(photoPath);
                if(!file.exists()){
                    b=file.mkdirs();
                    LogUtils.i("---create---directory---boolean:"+b);
                }else {
                    //UniversalUtils.delFile(file);
                }
            }
        }
        return photoPath;
    }

    public int getCacheSize(){
        return cacheSize;
    }
    public File getCacheFileDir(){
        fileDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"cache");
        if(!fileDir.exists()){
           fileDir.mkdirs();
        }
        return fileDir;
    }
}
