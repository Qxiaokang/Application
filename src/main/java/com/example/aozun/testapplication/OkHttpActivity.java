package com.example.aozun.testapplication;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.aozun.testapplication.activity.BaseActivity;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.OkHttp3Manager;
import com.example.aozun.testapplication.utils.OkHttpClientManager;
import com.example.aozun.testapplication.utils.UniversalUtils;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

/**
 * okhttp加载图片
 * picasso加载图片
 * glide加载图片
 * okhttp3请求
 */
public class OkHttpActivity extends BaseActivity implements View.OnClickListener{
    private Button otbut,dmStart;
    private ImageView imageView,picasso_iv,glide_iv;
    private static final String url="http://img3.fengniao.com/travel/2_960/1850.jpg";
    private static final String picurl1="http://nuuneoi.com/uploads/source/playstore/cover.jpg";
    private static final String picurl2="https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=gif%E5%9B%BE&step_word=&hs=2&pn=2&spn=0&di=5356811902&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=407946030%2C1201660130&os=3869529541%2C680616572&simid=3329441505%2C133996416&adpicid=0&lpn=0&ln=1982&fr=&fmq=1484646413391_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F19%2F20150519210608_j4BLQ.thumb.700_0.gif&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B17tpwg2_z%26e3Bv54AzdH3Frj5rsjAzdH3F4ks52AzdH3Fnm09bm0b8AzdH3F1jpwtsAzdH3F&gsm=0&rpstart=0&rpnum=0";
    //手机信息查询接口
    private static final String PHONE_NUMCONTENT="http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15071651718";
    private static final String TIANQI ="http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=0&city=&dfc=1&charset=utf-8";
    private static final String LOCATION_URL="http://ditu.amap.com/service/regeo?longitude=121.04925573429551&latitude=31.315590522490712";
    private  ProgressDialog progressDialog;
    //系统自带的downloadmanager
    private DownloadManager downloadManager;
    private  DownloadManager.Request request;
    private Timer timer=new Timer();
    private TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ok_http);
        initViews();


    }

    //下载处理
     Handler downloadHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 111:
                    if(progressDialog!=null&&progressDialog.isShowing()){
                        progressDialog.setProgress(msg.arg1);
                        if(msg.arg1==100){
                            progressDialog.dismiss();
                            UniversalUtils.getInstance().showToast(OkHttpActivity.this,"downloadManager下载完成");
                        }
                    }

                    break;
                default:
                    cancleDialog();
                    break;

            }

        }
    };
    //用DownloadManager进行下载
    private void startDownload(){
        downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        request=new DownloadManager.Request(Uri.parse(picurl1));
        //data下面
        //request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,"img3.jpg");
        //sd卡
        request.setDestinationInExternalPublicDir("images","img3.jpg");
        //指定下载网络类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        //是否允许漫游状态下执行下载操作
        request.setAllowedOverRoaming(false);
        //是否允许“计量式的网络连接”执行下载操作(默认是允许)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            request.setAllowedOverMetered(false);
        }

        //notifaction样式
        request.setTitle("Title");
        request.setDescription("描述");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//是否显示
        final long loadid=downloadManager.enqueue(request);
        final DownloadManager.Query query=new DownloadManager.Query();
        timerTask=new TimerTask(){
            @Override
            public void run(){
                Cursor cursor = downloadManager.query(query.setFilterById(loadid));
                if(cursor!=null&&cursor.moveToFirst()){
                    if(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))==DownloadManager.STATUS_SUCCESSFUL){
                        LogUtils.d("downloadmanager--100");
                        timerTask.cancel();
                        timerTask=null;
                    }
                    String title=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String address =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    int downloadIndex=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                    int downloadTotal=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    LogUtils.d("title:"+title+"  address:"+address+"  index:" +downloadIndex+"   total:"+downloadTotal);

                    int pro=downloadIndex*100/downloadTotal;
                    Message message=Message.obtain();
                    message.arg1=pro;
                    message.what=111;
                    downloadHandler.sendMessage(message);
                }
            }
        };
        timer.schedule(timerTask,0,200);

    }
    //
    private void initViews(){
        otbut= (Button) findViewById(R.id.okhttpbut);
        imageView= (ImageView) findViewById(R.id.imageid);
        otbut.setOnClickListener(this);
        picasso_iv= (ImageView) findViewById(R.id.picasso_iv);
        glide_iv= (ImageView) findViewById(R.id.glide_iv);
        dmStart= (Button) findViewById(R.id.downloadmanager_bt);
        dmStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.okhttpbut:
                //okhttp加载图片
                OkHttpClientManager.getInstance()._displayImage(imageView,url,110);
                //picasso加载图片
                Picasso.with(OkHttpActivity.this)
                        .load(picurl1)
                        .config(Bitmap.Config.ARGB_8888)
                        .placeholder(R.drawable.normal)
                        .error(R.drawable.normal)
                        .into(picasso_iv);
                //glide加载图片
                Glide.with(OkHttpActivity.this)
                        .load(picurl2)
                        .asGif()
                        .placeholder(R.drawable.normal)//点位
                        .error(R.drawable.normal)//错误图片
                        .into(glide_iv);

                //okhttp3网络请求
                OkHttp3Manager.getInstance().getAsynString(LOCATION_URL, new OkHttp3Manager.Ok3CallBack(){
                    @Override
                    public void error(String ee){
                        LogUtils.e(""+ee);

                    }

                    @Override
                    public void success(String ss){
                        LogUtils.d("请求的数据："+ss);
                    }
                });

            case R.id.downloadmanager_bt:
                showDialog();
                startDownload();

                break;
        }

    }
    //显示对话框
    private void showDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("下 载 进 度");
        progressDialog.setTitle("提 示");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }
    //取消
    private void cancleDialog(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy(){
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        super.onDestroy();
    }
}
