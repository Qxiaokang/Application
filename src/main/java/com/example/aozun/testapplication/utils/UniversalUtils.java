package com.example.aozun.testapplication.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by HHD-H-I-0369 on 2017/1/10.
 * 一些通用的工具
 */
public class UniversalUtils{
    private static UniversalUtils utils;
    private static  Toast toast;
    private UniversalUtils(){

    }
    //单例
    public synchronized static UniversalUtils getInstance(){
        if(utils==null){
            synchronized(UniversalUtils.class){
                if(utils==null){
                    utils=new UniversalUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 获取文件的md5值
     * @param path 文件的全路径名称
     * @return
     */
    public String getFileMd5(String path){
        try {
            // 获取一个文件的特征信息，签名信息。
            File file = new File(path);
            // md5
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            StringBuffer sb  = new StringBuffer();
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;//
                String str = Integer.toHexString(number);
                // System.out.println(str);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    //
    public void showToast(Context context,String text){
        if(toast==null){
            toast=Toast.makeText(context,text,Toast.LENGTH_LONG);
        }else {
            toast.setText(text);
        }
        toast.show();
    }

    /**  返回数据表主键所需的32位主键值   */
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(Locale.US);
    }
    /** 删除该目录下的所有文件  */
    public static boolean delFile(File f) {
        boolean flag=false;
        if (!f.exists()) {
            return true;
        }
        if (f.isDirectory()) {
            File[] fs = f.listFiles();
            for (int i = 0; i < fs.length; i++) {
                flag = delFile(fs[i]);
                if(!flag){
                    break;
                }
            }
        } else {
            flag=f.delete();
        }
        return flag;
    }
}
