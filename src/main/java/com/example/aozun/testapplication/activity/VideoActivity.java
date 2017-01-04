package com.example.aozun.testapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.example.aozun.testapplication.R;

import java.io.File;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 简单的弹幕效果
 */
public class VideoActivity extends Activity implements DrawHandler.Callback, View.OnClickListener{
    private VideoView videoView;
    private DanmakuView danmakuView;
    private boolean showDanmaku;
    private DanmakuContext danmakuContext;
    private LinearLayout sendLayout;
    private EditText send_et;
    private Button send_bt;
    private String videoPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"Download"+File.separator+"test.mp4";
    private BaseDanmakuParser parser = new BaseDanmakuParser(){
        @Override
        protected IDanmakus parse(){
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initViews();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener(){
            @Override
            public void onSystemUiVisibilityChange(int i){
                if(i == View.SYSTEM_UI_FLAG_VISIBLE){
                    onWindowFocusChanged(true);
                }
            }
        });

        //videoView.setVideoPath(videoPath);
       // videoView.start();

    }

    private void initViews(){
        videoView = (VideoView) findViewById(R.id.video);
        danmakuView = (DanmakuView) findViewById(R.id.danmaku_id);
        sendLayout = (LinearLayout) findViewById(R.id.send_layout);
        send_bt = (Button) findViewById(R.id.send_bt);
        send_et = (EditText) findViewById(R.id.send_et);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(this);
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
        danmakuView.setOnClickListener(this);
        send_bt.setOnClickListener(this);
    }

    @Override
    public void prepared(){
        showDanmaku = true;
        danmakuView.start();
        generateSomeDanmaku();
    }

    @Override
    public void updateTimer(DanmakuTimer timer){
    }

    @Override
    public void danmakuShown(BaseDanmaku danmaku){
    }

    @Override
    public void drawingFinished(){
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue){
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder){
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if(withBorder){
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(showDanmaku){
                    int time = new Random().nextInt(200);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try{
                        Thread.sleep(time);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(danmakuView != null && danmakuView.isPrepared()){
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()){
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        showDanmaku = false;
        if(danmakuView != null){
            danmakuView.release();
            danmakuView = null;
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.send_bt:
                String content=send_et.getText().toString();
                if(content!=null){
                    addDanmaku(content,true);
                    send_et.setText("");

                }
                sendLayout.setVisibility(View.GONE);
                break;
            case R.id.danmaku_id:
                if(sendLayout.getVisibility() == View.GONE){
                    sendLayout.setVisibility(View.VISIBLE);
                }else{
                    sendLayout.setVisibility(View.GONE);
                }
                break;
        }
    }
}
