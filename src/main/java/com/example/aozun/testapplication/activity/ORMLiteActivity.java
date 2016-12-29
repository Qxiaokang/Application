package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.User;
import com.example.aozun.testapplication.db.UserDao;

import java.sql.SQLException;
import java.util.List;

/**
 * ORMLite对数据库进行增删改查
 */
public class ORMLiteActivity extends AppCompatActivity implements View.OnClickListener{
    private Button add_bt, delete_bt, update_bt, query_bt;
    private TextView content_tv;//内容
    private int count=1;
    private int deleteId=1;
    private UserDao userDao=UserDao.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ormlite);
        initViews();

    }

    private void initViews(){
        add_bt = (Button) findViewById(R.id.bt_add);
        delete_bt = (Button) findViewById(R.id.bt_delete);
        update_bt = (Button) findViewById(R.id.bt_uptate);
        query_bt = (Button) findViewById(R.id.bt_query);
        content_tv= (TextView) findViewById(R.id.tv_content);
        add_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        update_bt.setOnClickListener(this);
        query_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_add:
                User user=new User("bj3435"+ count,"123456"+count);
                try{
                    userDao.addDb(user);
                    Toast.makeText(ORMLiteActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                    count++;
                }catch(SQLException e){
                    Toast.makeText(ORMLiteActivity.this,"添加失败",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.bt_delete:
                try{
                    userDao.deleteDb(deleteId);
                    Toast.makeText(ORMLiteActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                    deleteId++;
                }catch(SQLException e){
                    Toast.makeText(ORMLiteActivity.this,"删除失败",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.bt_uptate:
                try{
                        List<User> users = userDao.queryDb();
                    for(int i = 0; i < users.size(); i++){
                        userDao.updateDb(users.get(users.size()-1).getId()+"");
                        Toast.makeText(ORMLiteActivity.this, "更新成功", Toast.LENGTH_LONG).show();
                    }
                }catch(SQLException e){
                    Toast.makeText(ORMLiteActivity.this,"更新失败",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


                break;
            case R.id.bt_query:
                try{
                    StringBuilder builder=new StringBuilder();
                    List<User> users = userDao.queryDb();
                    for(int i = 0; i < users.size(); i++){
                        builder.append("id: "+users.get(i).getId()+"   userId: "+users.get(i).getUser_id()
                        +"   userPwd: "+users.get(i).getUser_pwd()+"\n");
                    }
                    content_tv.setText(builder.toString());
                }catch(SQLException e){
                    Toast.makeText(ORMLiteActivity.this,"查询失败",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
        }

    }
}
