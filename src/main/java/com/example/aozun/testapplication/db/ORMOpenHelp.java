package com.example.aozun.testapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.aozun.testapplication.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by HHD-H-I-0369 on 2016/12/28.
 * OrmLite数据库工具类
 */
public class ORMOpenHelp extends OrmLiteSqliteOpenHelper{
    private static final String DBNAME = "orm_test.db";
    private static ORMOpenHelp ormOpenHelp;

    //构造
    public ORMOpenHelp(Context context){
        super(context, DBNAME, null, 1);
    }

    public synchronized static ORMOpenHelp getInstance(Context context){
        if(ormOpenHelp == null){
            synchronized(ORMOpenHelp.class){
                ormOpenHelp = new ORMOpenHelp(context);
            }
        }
        return ormOpenHelp;
    }

    //创建
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource){
        try{
            TableUtils.createTable(connectionSource, User.class);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //更新
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion){
        if(oldVersion<newVersion){
            try{
                TableUtils.dropTable(connectionSource,User.class,true);
                onCreate(database,connectionSource);

            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }
}
