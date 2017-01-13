package com.example.aozun.testapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aozun.testapplication.activity.BaseActivity;
import com.example.aozun.testapplication.activity.LitePalActivity;
import com.example.aozun.testapplication.activity.LockActivity;
import com.example.aozun.testapplication.activity.MPChartActivity;
import com.example.aozun.testapplication.activity.ORMLiteActivity;
import com.example.aozun.testapplication.activity.Retrofit2Activity;
import com.example.aozun.testapplication.activity.RxActivtity;
import com.example.aozun.testapplication.activity.ViewActivity;
import com.example.aozun.testapplication.adapter.RecycleAdapter;
import com.example.aozun.testapplication.db.TestOpenHelp;
import com.example.aozun.testapplication.service.LockService;
import com.example.aozun.testapplication.utils.Netutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 可拖动并且可保存位置的RecyclerView
 */
public class MainActivity extends BaseActivity implements RecycleAdapter.RecycleonclickListener, RecycleAdapter.RecycleonLongclickListener{

    private RecyclerView hrecyclerView;
    private String buts[] = {"Retrofit", "DateSlide", "HGallery", "OkHttpUtil", "litepal", "ORMLite", "RxJava", "Views", "MPAndroidChart", "LockView", "DiscrollVableView"};//按钮为例
    private int dragFlags, swipeFlags;
    private RecycleAdapter rca;
    private List<String> datas = new ArrayList<>();
    private LinearLayoutManager linearmanager;
    private int position;
    private int left;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = TestOpenHelp.getInstance(this).getWritableDatabase();
        //初始化list
        for(int i = 0; i < buts.length; i++){
            datas.add(buts[i]);
        }
        initViews();//初始化
        //开启服务
        Intent intent=new Intent(MainActivity.this,LockService.class);
        startService(intent);

    }

    private void initViews(){
        //查询name字段
        Map<String, Object> map = TestOpenHelp.getInstance(this).queryMap("select name from recyclerlist ");
        if(map.size() == 0){
            //查询数据库为空，则将本地的数据存处数据库
            String sss[] = new String[datas.size()];
            for(int i = 0; i < datas.size(); i++){
                sss[i] = datas.get(i);
            }
            if(sss != null){
                Log.e("array:", Arrays.toString(sss));
                database.execSQL("insert into recyclerlist(reid,name) values(11,'" + Arrays.toString(sss) + "')");//数据库没有则添加
            }
        }else{
            //查询数据不为空，将数据库里的数据存入list
            String name = (String) map.get("name");
            String replace = name.replace("[", "").replace("]", "").replace(" ", "");
            Log.e("printSqlName:", replace);
            String[] strings = replace.split(",");
            datas.clear();
            for(int i = 0; i < strings.length; i++){
                datas.add(strings[i]);
            }
        }
        hrecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        linearmanager = new LinearLayoutManager(this);
        linearmanager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置recyclerVeiw横向滚动
        //读取内存中存储的recyclerView屏幕显示的位置，退出，重新进入的时候回到上次显示的位置
        SharedPreferences sharedPreferences = getSharedPreferences("num", MODE_PRIVATE);
        int position1 = sharedPreferences.getInt("position", 0);
        int left1 = sharedPreferences.getInt("left", 0);
        linearmanager.scrollToPositionWithOffset(position1, left1);
        hrecyclerView.setLayoutManager(linearmanager);
        hrecyclerView.setItemAnimator(new DefaultItemAnimator());
        rca = new RecycleAdapter(this, datas);
        hrecyclerView.setAdapter(rca);
        rca.setOnItemclickListener(this);
        rca.setOnlongItemclickListener(this);
        itemTouchHelper.attachToRecyclerView(hrecyclerView);
        Log.e("onCreate:", "position:" + position1 + "left:" + left1);
    }

    @Override
    public void onitemListener(View view, String data){
        Intent intent = null;
        if(data != null){
            Toast.makeText(this, "点击" + data.toString(), Toast.LENGTH_LONG).show();
            if(data.equals(buts[0])){
                if(Netutil.connetWork(this)){
                   intent=new Intent(MainActivity.this, Retrofit2Activity.class);
                }else{
                    Toast.makeText(this, "未联网,不能进入下一页", Toast.LENGTH_LONG).show();
                }
            }
            if(data.equals(buts[1])){
                intent = new Intent(MainActivity.this, FirstActivity.class);
            }
            if(data.equals(buts[2])){
                intent = new Intent(MainActivity.this, HrActivity.class);
            }
            if(data.equals(buts[3])){
                intent = new Intent(MainActivity.this, OkHttpActivity.class);
            }
            if(data.equals(buts[4])){
                intent=new Intent(MainActivity.this,LitePalActivity.class);
            }
            if(data.equals(buts[5])){
                intent=new Intent(MainActivity.this, ORMLiteActivity.class);
            }
            if(data.equals(buts[6])){
                intent=new Intent(MainActivity.this, RxActivtity.class);
            }
            if(data.equals(buts[7])){
                intent=new Intent(MainActivity.this,ViewActivity.class);
            }
            if(data.equals(buts[8])){
                intent=new Intent(MainActivity.this,MPChartActivity.class);
            }
            if(data.equals(buts[9])){
                intent=new Intent(MainActivity.this, LockActivity.class);
            }
            if(intent != null){
                MainActivity.this.startActivity(intent);
            }

        }
    }

    @Override
    public void onLongItemListener(View v, String data){
        if(data != null){
            Toast.makeText(this, "長按 " + data, Toast.LENGTH_LONG).show();
        }
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback(){
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
            if(hrecyclerView.getLayoutManager() instanceof GridLayoutManager){
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }else{
                dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        //移动
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if(fromPosition < toPosition){
                for(int i = fromPosition; i < toPosition; i++){
                    Collections.swap(datas, i, i + 1);
                }
            }else{
                for(int i = fromPosition; i > toPosition; i--){
                    Collections.swap(datas, i, i - 1);
                }
            }
            rca.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState){
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }

        @Override
        public boolean isLongPressDragEnabled(){
            //return super.isLongPressDragEnabled();
            return true;
        }
    });

    @Override
    protected void onDestroy(){
        position = linearmanager.findFirstVisibleItemPosition();//屏幕显示的position
        View view = linearmanager.findViewByPosition(position);
        if(view != null){
            left = view.getLeft();
        }
        Log.e("print:", "position:" + position + "left:" + left);
        //用sharePreferences存储recyclerView的位置
        SharedPreferences sharedPreferences = getSharedPreferences("num", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("position", position);
        edit.putInt("left", left);
        edit.commit();
        //退出时将list数据重新存入数据库，保存list的顺序
        updqtedb();


        //关闭服务
        Intent intent=new Intent(MainActivity.this, LockService.class);
        stopService(intent);
        super.onDestroy();
    }

    //更新数据库
    public void updqtedb(){
        database.execSQL("delete from recyclerlist");
        String sss[] = new String[datas.size()];
        for(int i = 0; i < datas.size(); i++){
            sss[i] = datas.get(i);
        }
        if(sss != null){
            database.execSQL("insert into recyclerlist (reid,name) values (11,'" + Arrays.toString(sss) + "')");
        }
    }

}
