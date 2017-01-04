package com.example.aozun.testapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aozun.testapplication.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

/**
 * Created by HHD-H-I-0369 on 2017/1/4.
 */
public class RadarCharFragment extends Fragment{
    private View view;
    private RadarChart radarChart;
    public ArrayList<String> x = new ArrayList<String>();
    public ArrayList<Entry> y = new ArrayList<Entry>();
    public ArrayList<RadarDataSet> radarDataSets = new ArrayList<RadarDataSet>();
    public RadarData radarData = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.radarchar_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        radarChart= (RadarChart) view.findViewById(R.id.RadarChar_f);
        RadarData resultLineData  = getRadarData(40, 100);
        showChart();
    }

    /**
     * gv
     * 初始化数据
     * count 表示坐标点个数，range表示等下y值生成的范围
     */
    public RadarData getRadarData(int count, float range) {
        for (int i = 0; i < count; i++) {  //X轴显示的数据
            x.add(i + "");
        }
        for (int i = 0; i < count; i++) {//y轴的数据
            float result = (float) (Math.random() * range) + 3;
            y.add(new Entry(result, i));
        }
        RadarDataSet radarDataSet = new RadarDataSet(y, "雷达图");//y轴数据集合
        radarDataSet.setLineWidth(1f);//线宽
        radarDataSet.setColor(Color.RED);//现实颜色
        radarDataSet.setHighLightColor(Color.WHITE);//高度线的颜色
        radarDataSets.add(radarDataSet);
        radarData = new RadarData(x, radarDataSets);
        return radarData;
    }

    /**
     * 设置样式
     */
    public void showChart() {
        //        radarChart.setDrawBorders(false);//是否添加边框
        radarChart.setDescription("有风险的数据");//数据描述
        radarChart.setNoDataTextDescription("我需要数据");//没数据显示
        //        radarChart.setDrawGridBackground(true);//是否显示表格颜色
        radarChart.setBackgroundColor(Color.parseColor("#8C9BD0"));//背景颜色
        radarChart.setData(radarData);//设置数据
        Legend legend = radarChart.getLegend();//设置比例图片标示，就是那一组Y的value
        legend.setForm(Legend.LegendForm.CIRCLE);//样式
        legend.setFormSize(6f);//字体
        legend.setTextColor(Color.WHITE);//设置颜色
        radarChart.animateY(2000);//X轴的动画
    }
}
