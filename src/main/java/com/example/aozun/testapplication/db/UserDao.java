package com.example.aozun.testapplication.db;

import android.content.Context;

import com.example.aozun.testapplication.bean.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2016/12/28.
 */
public class UserDao{
    private static UserDao userDao;
    private Context context;
    private Dao<User, Integer> users;

    //单例
    public static synchronized UserDao getInstance(Context context){
        if(userDao == null){
            synchronized(UserDao.class){
                if(userDao == null){
                    userDao = new UserDao(context);
                }
            }
        }
        return userDao;
    }

    private UserDao(Context context){
        this.context = context;
    }

    //增
    public void addDb(User user) throws SQLException{
        getUserDao().create(user);
    }

    //删
    public void deleteDb(int id) throws SQLException{
        getUserDao().deleteById(id);
    }
    //改
    public void updateDb(User user) throws SQLException{

            getUserDao().update(user);

    }
    //查
    public List<User> queryDb() throws SQLException{
        return getUserDao().queryForAll();
    }


    public Dao<User, Integer> getUserDao() throws SQLException{
        if(users == null){
            users = ORMOpenHelp.getInstance(context).getDao(User.class);
        }
        return users;
    }
}
