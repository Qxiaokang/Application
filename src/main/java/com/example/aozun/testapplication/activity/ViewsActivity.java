package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.UniversalUtils;
import com.example.aozun.testapplication.views.ShopButton;

/**
 * 自定义view类
 */

public class ViewsActivity extends  BaseActivity implements View.OnClickListener{
    private Button snackDel,snackAdd,endbt;//删除,添加,添加完成的按钮
    private ShopButton shopButton;//加入购物车的按钮
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        initViews();

    }
    //views初始化
    private void initViews(){
        snackDel = (Button) findViewById(R.id.snack_del_id);
        snackDel.setOnClickListener(this);
        snackAdd= (Button) findViewById(R.id.snack_add_id);
        snackAdd.setOnClickListener(this);
        endbt= (Button) findViewById(R.id.end_id);
        endbt.setOnClickListener(this);
        shopButton= (ShopButton) findViewById(R.id.shopbt_id);
        shopButton.setShopText("加入购物车");
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.snack_del_id:
                showDelSnackbar();
                break;
            case R.id.snack_add_id:
                showAddSnackbar();
                break;
            case R.id.end_id:
                UniversalUtils.getInstance().showToast(ViewsActivity.this,"添加商品数量为"+shopButton.getMcount());
                break;
            default:
                break;

        }
    }
    //删除的snackbar
    private void showDelSnackbar(){
        Snackbar.make(snackDel,"是否删按钮？",Snackbar.LENGTH_LONG)
                .setAction("是", new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        shopButton.setClickable(false);
                        shopButton.setVisibility(View.INVISIBLE);
                        UniversalUtils.getInstance().showToast(ViewsActivity.this,"删除成功");
                    }
                }).show();
    }
    //添加的snackbar
    private void showAddSnackbar(){
        Snackbar.make(snackDel,"是否添加按钮？",Snackbar.LENGTH_LONG)
                .setAction("是", new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        shopButton.setVisibility(View.VISIBLE);
                        shopButton.setClickable(true);
                        UniversalUtils.getInstance().showToast(ViewsActivity.this,"添加成功");
                    }
                }).show();
    }
}
