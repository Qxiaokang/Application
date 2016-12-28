package com.example.aozun.testapplication.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by HHD-H-I-0369 on 2016/12/28.
 * 数据库User类
 */
@DatabaseTable(tableName = "T_USER")//表名
public class User{
    @DatabaseField(generatedId = true)//主键自增长
    private int id;
    @DatabaseField(columnName = "t_user_id")//列名
    private String user_id;
    @DatabaseField(columnName = "t_user_pwd")
    private String user_pwd;
    //无参构造
    public User(){

    }
    //带参构造
    public User(String user_id,String user_pwd){
        this.user_id=user_id;
        this.user_pwd=user_pwd;

    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getUser_pwd(){
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd){
        this.user_pwd = user_pwd;
    }
}
