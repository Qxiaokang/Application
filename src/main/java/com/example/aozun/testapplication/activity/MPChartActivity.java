package com.example.aozun.testapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.fragment.BarChartFragment;
import com.example.aozun.testapplication.fragment.CandleStickChartFragment;
import com.example.aozun.testapplication.fragment.LineChartFragment;
import com.example.aozun.testapplication.fragment.PieChartFragment;
import com.example.aozun.testapplication.fragment.RadarCharFragment;
import com.example.aozun.testapplication.fragment.ScatterChartFragment;
import com.example.aozun.testapplication.utils.MainApplication;

/**
 * 图表类
 */

public class MPChartActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup mpGroup;
    private Fragment[] fragments = null;
    private FragmentTransaction fragmentTransaction;
    private int lastIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MainApplication.getInstance().addActivity(this);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mpchart);

        initViews();
        initFragment();

    }

    private void initFragment(){
        fragments = new Fragment[]{new LineChartFragment(),new BarChartFragment(),new PieChartFragment()
        ,new ScatterChartFragment(),new CandleStickChartFragment(),new RadarCharFragment()};
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(int i = 0; i < fragments.length; i++){
            fragmentTransaction.add(R.id.fragment_frame, fragments[i]);
            fragmentTransaction.hide(fragments[i]);//先添加后隐藏
        }
        fragmentTransaction.show(fragments[0]);
        fragmentTransaction.commit();
    }

    private void initViews(){
        mpGroup = (RadioGroup) findViewById(R.id.mp_group);
        mpGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i){
        int index = 0;
        switch(i){
            case R.id.LineChart_id:
                index = 0;
                break;
            case R.id.BarChart_id:
                index = 1;
                break;
            case R.id.PieChart_id:
                index=2;
                break;
            case R.id.ScatterChart_id:
                index=3;
                break;
            case R.id.CandleStickChart_id:
                index=4;
                break;
            case R.id.RadarChar_id:
                index=5;
                break;
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments[lastIndex]);//隐藏上一个
        fragmentTransaction.show(fragments[index]);//显示当前的
        fragmentTransaction.commit();//提交
        lastIndex = index;
    }
}
