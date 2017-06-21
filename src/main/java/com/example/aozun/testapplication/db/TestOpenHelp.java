package com.example.aozun.testapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aozun.testapplication.utils.LogUtils;

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
    private static final String T_PIC="create table t_pic(pic_id integer primary key,pic_name varchar(100) not null,pic_path varchar(300) not null)";
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(RECYCLER);
        db.execSQL(T_PIC);
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

    /**  新增数据  */
    public boolean insert(String tableName,String [] fields,String [] fieldVals){
        ContentValues cv=new ContentValues();
        for(int i=0;i<fields.length;i++){
            cv.put(fields[i], fieldVals[i]);
        }
        if(db.insert(tableName, null, cv)==1){
            return true;
        }
        return false;
    }

    /**  更新数据   */
    public boolean update(String tableName,String [] fields,String [] fieldVals,String where){
        ContentValues cv=new ContentValues();
        for(int i=0;i<fields.length;i++){
            cv.put(fields[i], fieldVals[i]);
        }
        int m = db.update(tableName, cv, where, null);
        if(m==1){
            return true;
        }
        return false;
    }

    /** 插入、更新、删除 数据  */
    public boolean cudDB(String sql){
        boolean b = false;
        //如果要对数据进行更改，就调用此方法得到用于操作数据库的实例,该方法以读和写方式打开数据库
        db.beginTransaction();   // 开始事务
        try{
            db.execSQL(sql);
            //调用此方法会在执行到endTransaction() 时提交当前事务，如果不调用此方法会回滚事务
            db.setTransactionSuccessful();
            b = true;
        }catch(SQLException e){
            LogUtils.d("sql:"+e.toString());
            b=false;
        }finally{
            db.endTransaction();//由事务的标志决定是提交事务，还是回滚事务
        }
        return b;
    }
}
