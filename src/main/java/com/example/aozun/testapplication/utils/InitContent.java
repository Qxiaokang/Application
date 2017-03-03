package com.example.aozun.testapplication.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by HHD-H-I-0369 on 2017/3/2.
 * 初始化类
 */
public class InitContent{
    private static InitContent initContent;
    private String photoPath;

    private InitContent(){
    }

    public static synchronized InitContent getInstance(){
        if(initContent == null){
            initContent = new InitContent();
        }
        return initContent;
    }

    public String getPhotoPath(){
        //sd卡挂载成功，返回路径
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            photoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "images" + File.separator + "takephotos";
            File file = new File(photoPath);
            if(!file.exists()){
                file.mkdirs();
            }else {
                UniversalUtils.delFile(file);
            }
        }else{
            photoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "images" + File.separator + "takephotos";
            File file = new File(photoPath);
            if(!file.exists()){
                file.mkdirs();
            }else {
                UniversalUtils.delFile(file);
            }

            LogUtils.e("sd卡挂载失败");
        }
        return photoPath;
    }
}
