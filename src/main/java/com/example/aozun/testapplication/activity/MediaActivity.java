package com.example.aozun.testapplication.activity;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.aozun.testapplication.R;

import java.io.File;
import java.io.IOException;

public class MediaActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener{
    private Button start,stop,cancel;
    private MediaRecorder mediaRecorder;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private android.hardware.Camera camera;
    private boolean surfaceIsCreate=false,isRecording=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//no title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//set fullscreen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//set landscreen
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_media);
        initViews();
    }
    //init
    private void initViews(){
        start= (Button) findViewById(R.id.bt_start);
        stop= (Button) findViewById(R.id.bt_stop);
        cancel= (Button) findViewById(R.id.bt_cancel);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        cancel.setOnClickListener(this);
        surfaceView= (SurfaceView) findViewById(R.id.media_surface);
        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        surfaceIsCreate=true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2){
        startPreview();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        surfaceIsCreate=false;
        if(surfaceView!=null){
            surfaceView=null;
        }
        if(mediaRecorder!=null){
            mediaRecorder=null;
        }
        if(surfaceHolder!=null){
            surfaceHolder=null;
        }
    }

    private  void startPreview(){
        if(camera!=null&&!surfaceIsCreate){
            return;
        }

        try{
            camera=Camera.open(0);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        if(isRecording){
            stopRecording();
        }
        stopPreview();
    }

    private void stopRecording(){
        if(camera!=null){
            camera.lock();
        }
        if(mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
        isRecording=false;
    }

    private void stopPreview(){
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_start:
                mediaRecorder=new MediaRecorder();
                camera.unlock();
                mediaRecorder.setCamera(camera);
                //set video from camera
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置从麦克风采集声音
                //set video outputFormat THREE_GPP is 3gp  MPEG_4 is mp4
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                //音频编码
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                //set videoEncoder h263 h264  视频编码
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                //设置分辨率   必须放在设置格式和编码之后
                mediaRecorder.setVideoSize(176,144);
                mediaRecorder.setVideoFrameRate(20);
                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"test.mp4");
                isRecording=true;
                try{
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                }catch(Exception e){

                }
                break;
            case R.id.bt_stop:
                stopRecording();
                break;
            case R.id.bt_cancel:
                break;
            default:

                break;
        }
    }
}
