package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.UniversalUtils;

public class  SnackbarActivity extends  BaseActivity implements View.OnClickListener{
    private Button snack;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        initViews();

    }
    private void initViews(){
        snack= (Button) findViewById(R.id.snack_id);
        snack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.snack_id:
                showSnackbar();
                break;
            default:
                break;

        }
    }
    private void showSnackbar(){
        Snackbar.make(snack,"确定删除？",Snackbar.LENGTH_LONG)
                .setAction("是", new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        UniversalUtils.getInstance().showToast(SnackbarActivity.this,"删除成功");
                    }
                }).show();
    }
}
