package com.example.aozun.testapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.adapter.RecycleImageAdapter;
import com.example.aozun.testapplication.bean.ImageInfo;
import com.example.aozun.testapplication.bean.ImageMessage;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.OkHttp3Manager;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShowImageActivity extends BaseActivity{
    private RecycleImageAdapter aid = null;
    private RecyclerView rv = null;
    private ImageInfo imagInfo = null;
    private boolean isOk = false;
    private ProgressDialog pd;
    public static final String imgUrl = "http://www.imooc.com/api/teacher?type=4&num=30";
    private List<ImageMessage> listimage = new ArrayList<>();
    private Timer timer = null;
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        showProgress();
        initViews();
    }

    private void initViews(){
        rv = (RecyclerView) findViewById(R.id.image_recycle);
        FlexboxLayoutManager flm = new FlexboxLayoutManager();
        flm.setFlexWrap(FlexWrap.WRAP);
        flm.setFlexDirection(FlexDirection.ROW);
        flm.setAlignItems(AlignItems.STRETCH);
        flm.setJustifyContent(JustifyContent.FLEX_START);
        rv.setLayoutManager(flm);
        aid = new RecycleImageAdapter(ShowImageActivity.this, listimage, true);
        rv.setAdapter(aid);
        timer = new Timer();
        getListImage();
        timerTask = new TimerTask(){
            @Override
            public void run(){
                if(listimage.size() > 0){
                    imghandler.sendEmptyMessage(110);
                }
            }
        };
        timer.schedule(timerTask, 2000);
    }

    Handler imghandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 110:
                    if(pd != null && pd.isShowing()){
                        pd.dismiss();
                    }
                    LogUtils.e("---handler:" + 110);
                    LogUtils.e("---imageurl--0:" + listimage.get(0).getPicSmall());
                    aid.notifyDataSetChanged();
                    timer.cancel();
                    timerTask.cancel();
                    timerTask = null;
                    timer = null;
                    break;
                default:
                    break;
            }
        }
    };

    private void showProgress(){
        pd = new ProgressDialog(ShowImageActivity.this, ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(true);
        pd.setIndeterminate(true);
        pd.setIcon(android.R.drawable.ic_dialog_info);
        pd.setTitle("正在下载");
        pd.setMax(100);
        pd.setMessage("加载中...");
        pd.show();
    }

    private List<ImageMessage> getListImage(){
        final Gson gson = new Gson();
        OkHttp3Manager.getInstance().getAsynString(imgUrl, new OkHttp3Manager.Ok3CallBack(){
            @Override
            public void error(String ee){
                LogUtils.e("---okhttp3----faild");
            }

            @Override
            public void success(String ss){
                LogUtils.d("---okhtttp3---success");
                imagInfo = gson.fromJson(ss, ImageInfo.class);
                listimage.clear();
                listimage.addAll(imagInfo.getData());
                isOk = true;
                LogUtils.d("listimage.size:" + listimage.size());
            }
        });
        return listimage;
    }
}
