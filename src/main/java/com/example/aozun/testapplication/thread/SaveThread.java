package com.example.aozun.testapplication.thread;

import com.example.aozun.testapplication.utils.UniversalUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HHD-H-I-0369 on 2017/3/6.
 * 保存图片的线程
 */
public class SaveThread extends Thread{
    private String ph;
    private byte[] bytes;
    private File file;
    private FileOutputStream outputStream;

    public SaveThread(String path, byte[] bytes){
        this.ph = path;
        this.bytes = bytes;
    }

    @Override
    public void run(){
        super.run();
        try{
            file = new File(ph + File.separator + UniversalUtils.getInstance().getUUID() + ".jpg");
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes, 0, bytes.length);
            outputStream.close();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(outputStream!=null){
                try{
                    outputStream.close();

                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
