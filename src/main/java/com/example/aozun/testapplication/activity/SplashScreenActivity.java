package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.aozun.testapplication.MainActivity;
import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.db.TestOpenHelp;
import com.example.aozun.testapplication.utils.ComUtil;
import com.example.aozun.testapplication.utils.InitContent;
import com.example.aozun.testapplication.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * splash screen auto start activity
 */

public class SplashScreenActivity extends BaseActivity implements View.OnClickListener{
    private static final String pic_url1 = "http://hiphotos.baidu.com/wishwingliao/pic/item/e14cd4163789585d962b4379.jpg";
    private static final String pic_url2 = "http://img1.imgtn.bdimg.com/it/u=3866434847,1736286841&fm=214&gp=0.jpg";
    private static final String pic_url3 = "http://hiphotos.baidu.com/wishwingliao/pic/item/e14cd4163789585d962b4379.jpg";
    private ImageView iv_splash;
    private Button bt_splash;
    private String pic_name;
    private String pic_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViews();
    }

    private void initViews(){
        String[] split = pic_url3.split("//")[1].split("/");
        pic_name = split[split.length - 1].replaceAll("=", "").replaceAll(",", "");
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        bt_splash = (Button) findViewById(R.id.bt_splash);
        iv_splash.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_splash.setOnClickListener(this);
        bt_splash.setOnClickListener(this);
        downLoadImage(pic_url3, InitContent.getInstance().getPhotoPath(), pic_name);
        countDownTimer.start();
    }

    //start thread to download image
    private void downLoadImage(final String url, final String path, final String pic_name){
        new Thread(){
            @Override
            public void run(){
                super.run();
                boolean isok = getpicUrlandSave(url, path, pic_name);
                if(isok){
                    downloadHandler.sendEmptyMessage(1);
                }else{
                    downloadHandler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    //downLoad and save pictures
    private boolean getpicUrlandSave(String url, String path, String pic_name){
        boolean b = true;
        InputStream in = null;
        try{
            URL url1 = new URL(url);
            HttpURLConnection hr = (HttpURLConnection) url1.openConnection();
            hr.setConnectTimeout(5000);
            hr.setRequestMethod("GET");
            hr.connect();
            LogUtils.e("image----path:" + path + "   namepic:" + pic_name);
            if(hr.getResponseCode() == HttpURLConnection.HTTP_OK){
                in = hr.getInputStream();
                File fi = new File(path + File.separator + pic_name);
                if(!fi.exists()){
                    fi.createNewFile();
                }else{
                    fi.delete();
                    fi.createNewFile();
                }
                byte[] by = new byte[1024];
                int len = 0;
                FileOutputStream fileOutputStream = new FileOutputStream(fi);
                while((len = in.read(by)) != -1){
                    fileOutputStream.write(by, 0, len);
                    fileOutputStream.flush();
                }
                in.close();
                fileOutputStream.close();
                b = TestOpenHelp.getInstance(getApplicationContext()).cudDB("delete from t_pic where pic_name='" + pic_name + "'");
                b = TestOpenHelp.getInstance(getApplicationContext()).cudDB("insert into t_pic (pic_name,pic_path) values ('" + pic_name + "','" + path + File.separator + pic_name + "')");
                LogUtils.e("---sql--boolean:" + b);
            }
        }catch(java.io.IOException e){
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    //select from sqllite and init imageView
    private boolean initImage(){
        boolean b = true;
        String sqlstr = "select * from t_pic order by pic_id desc";
        List<Map<String, Object>> querylist = TestOpenHelp.getInstance(getApplicationContext()).query(sqlstr);
        try{
            if(querylist != null && querylist.size() > 0){
                pic_path = querylist.get(0).get("pic_path").toString();
                iv_splash.setImageBitmap(BitmapFactory.decodeFile(pic_path));
            }else{
                iv_splash.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.systembg));
            }
        }catch(Exception e){
            b = false;
        }
        return b;
    }

    //
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.iv_splash:
                break;
            case R.id.bt_splash:
                gotomainActivity();
                break;
            default:
                break;
        }
    }

    // start intent
    private void gotomainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        if(pic_path != null){
            intent.putExtra("pic_path", pic_path);
        }
        finish();
        SplashScreenActivity.this.startActivity(intent);
    }

    //count down
    CountDownTimer countDownTimer = new CountDownTimer(4000, 1000){
        @Override
        public void onTick(long l){
            bt_splash.setText("跳过(" + l / 1000 + "s)");
        }

        @Override
        public void onFinish(){
            gotomainActivity();
        }
    };

    //destroty count down
    @Override
    protected void onDestroy(){
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
        super.onDestroy();
    }

    Handler downloadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    initImage();
                    break;
                case 0:
                    initImage();
                    ComUtil.getInstance(getApplicationContext()).showToast("update image faild");
                    break;
                default:
                    break;
            }
        }
    };
}
