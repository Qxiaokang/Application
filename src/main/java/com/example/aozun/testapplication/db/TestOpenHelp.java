package com.example.aozun.testapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HHD-H-I-0369 on 2016/11/17.
 */
public class TestOpenHelp extends SQLiteOpenHelper{
    private static final String TEST_DB = "testrec.db";
    private static final int DB_VERSION = 2;
    private static TestOpenHelp testOpenHelp;
    private Context context;
    private SQLiteDatabase db;

    private TestOpenHelp(Context context){
        super(context, TEST_DB, null, DB_VERSION);
        this.context = context;
        db = getWritableDatabase();
    }

    //单例
    public static TestOpenHelp getInstance(Context context){
        if(testOpenHelp == null){
            synchronized(TestOpenHelp.class){
                if(testOpenHelp == null){
                    testOpenHelp = new TestOpenHelp(context);
                }
            }
        }
        return testOpenHelp;
    }

    private static final String RECYCLER = "create table recyclerlist(reid integer primary key,name varchar(1000) not null)";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(RECYCLER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion>oldVersion){
         db.execSQL("drop table recyclerlist");
         onCreate(db);
        }
    }

    //查询数据，存入list集合
    public  List<Map<String, Object>> query(String sql){
        Cursor cursor = null;
        try{
            List<Map<String, Object>> list = new ArrayList<>();
            cursor = getCursor(sql);
            Map<String, Object> map = null;
            if(cursor != null && cursor.getCount() > 0){
                int count = cursor.getCount();
                int columnCount = cursor.getColumnCount();
                String columnCountName[] = new String[columnCount];
                for(int i = 0; i < columnCount; i++){
                    columnCountName[i] = cursor.getColumnName(i);
                }
                cursor.moveToFirst();
                for(int j = 0; j < count; j++){
                    map = new HashMap<String, Object>();
                    for(int i = 0; i < columnCount; i++){
                        map.put(columnCountName[i], cursor.getString(cursor.getColumnIndex(columnCountName[i])) == null ? "" : cursor.getString(cursor.getColumnIndex(columnCountName[i])));
                    }
                    list.add(map);
                    cursor.moveToNext();
                }
            }
            return list;
        }catch(Exception e){
            return null;
        }finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
    }
    //取一条查询记录放入Map
    public  Map<String, Object> queryMap(String sql){
        Cursor cursor = null;
        Map<String, Object> map = new HashMap<>();
        try{
            cursor = getCursor(sql);
            if(cursor != null && cursor.getCount() > 0){
                int columnCount = cursor.getColumnCount();
                String columnName[] = new String[columnCount];
                for(int i = 0; i < columnCount; i++){
                    columnName[i] = cursor.getColumnName(i);
                }
                cursor.moveToFirst();
                for(int i = 0; i < columnCount; i++){
                    String value = cursor.getString(cursor.getColumnIndex(columnName[i]));
                    map.put(columnName[i], value == null ? "" : value);
                }
            }
        }catch(Exception e){
            return null;
        }finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();//关闭
            }
        }
        return map;
    }

    public Cursor getCursor(String sql){
        return db.rawQuery(sql, null);
    }
}
