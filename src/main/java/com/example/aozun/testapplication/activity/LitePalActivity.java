package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.Books;
import com.example.aozun.testapplication.utils.LogUtils;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * 利用litepal操作数据库
 *注：添加 android:name="org.litepal.LitePalApplication"
 *
 新增表

 1) 首先新增实体类，如新增实体类addDemo

 2) 配置assets\litepal.xml文件，在<list></list>中增加映射关系

 3) 修改litepal.xml文件中的版本号，需要+1

 表字段修改

 1)  直接修改对应的实体类

 2)  修改litepal.xml文件中的版本号，需要+1
 */
public class LitePalActivity extends BaseActivity implements View.OnClickListener{
    private Button add_bt, delete_bt, update_bt, query_bt;
    private TextView content_tv;//内容
    private List<Books> books;
    private int index=1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
        Connector.getDatabase();//创建数据库，
        initViews();//初始化
    }

    private void initViews(){
        add_bt = (Button) findViewById(R.id.bt_add);
        delete_bt = (Button) findViewById(R.id.bt_delete);
        update_bt = (Button) findViewById(R.id.bt_uptate);
        query_bt = (Button) findViewById(R.id.bt_query);
        content_tv = (TextView) findViewById(R.id.tv_content);
        add_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        update_bt.setOnClickListener(this);
        query_bt.setOnClickListener(this);
    }


    //增
    private void addBooks(){
        Books book1 = new Books("疯狂Java", "66.34", "20163811");
        Books book2 = new Books("第一行代码", "70.66", "20161112");
        Books book3 = new Books("疯狂Android", "55.55", "20160304");
        book1.save();
        book2.save();
        book3.save();
    }

    //查询所有
    private List<Books> queryBooks(){
       /* List<Books> list = DataSupport.where("name=?", "疯狂Java").find(Books.class);//条件查询
        LogUtils.e("id:"+list.get(0).getId()+"  name:"+list.get(0).getName()+"  price:"
                +list.get(0).getPrice()+"  date:"+list.get(0).getDate());*/
        return DataSupport.findAll(Books.class);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_add:
                addBooks();
                break;
            //删除
            case R.id.bt_delete:
                books = queryBooks();
                for(int i = 0; i < books.size(); i++){
                    DataSupport.delete(Books.class,books.get(books.size()-1).getId());
                }
                break;
            //更新
            case R.id.bt_uptate:
                if(index>books.size()){
                    index=1;
                }
                Books book=new Books("新版java"+index,"99.99","20161212");
                books=queryBooks();

                for(int i = 0; i <books.size() ; i++){
                    book.update( books.get(books.size()-index).getId());
                }
                index++;
                break;
            //查询
            case R.id.bt_query:
                books = queryBooks();
                StringBuilder stringBuilder=new StringBuilder();
                for(int i = 0; i < books.size(); i++){
                  stringBuilder.append("id: "+books.get(i).getId()+"   name: "+books.get(i).getName()
                          +"  price: "+books.get(i).getPrice()+"  date: "+books.get(i).getDate()+"\n");

                }
                content_tv.setText(stringBuilder.toString());
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        LogUtils.i("litepal---ondestroy");
    }
}
