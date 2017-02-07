package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.adapter.RenRenImgAdapter;
import com.example.aozun.testapplication.bean.CardConfig;
import com.example.aozun.testapplication.manager.OverLayCardLayoutManager;
import com.example.aozun.testapplication.utils.MainApplication;
import com.example.aozun.testapplication.utils.RenRenCallback;

import java.util.ArrayList;
import java.util.List;

public class RenRenActivity extends BaseActivity{
    private RecyclerView recyclerView;
    List<Integer>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_ren_ren);
        initViews();
    }

    private void initViews(){
        recyclerView= (RecyclerView) findViewById(R.id.renren_recycler);
        OverLayCardLayoutManager manager=new OverLayCardLayoutManager();
        recyclerView.setLayoutManager(manager);
        CardConfig.initConfig(this);
        RenRenImgAdapter adapter=new RenRenImgAdapter(RenRenActivity.this);

        list.clear();
        for(int i = 0; i < adapter.IMAGES.length; i++){
            list.add(adapter.IMAGES[i]);
        }
        RenRenCallback callback=new RenRenCallback(recyclerView,adapter,list);
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }
}
