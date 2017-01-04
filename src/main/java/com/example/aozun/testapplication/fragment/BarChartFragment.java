package com.example.aozun.testapplication.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aozun.testapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultYAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by HHD-H-I-0369 on 2017/1/4.
 */
public class BarChartFragment extends Fragment{
    private BarChart barChart;
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.barchart_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        barChart = (BarChart) view.findViewById(R.id.barchar_f);
        initBarChart();
    }

    private void initBarChart(){
        //设置矩形阴影是否显示
        barChart.setDrawBarShadow(false);
        //设置值是否在矩形的上方显示
        barChart.setDrawValueAboveBar(true);
        //设置右下角描述
        barChart.setDescription("测试");
        //没用数据时显示
        barChart.setNoDataText("没有数据");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);
        // 设置是否可以触摸
        barChart.setTouchEnabled(true);
        // 是否可以拖拽
        barChart.setDragEnabled(true);
        // 是否可以缩放
        barChart.setScaleEnabled(true);
        // 集双指缩放
        barChart.setPinchZoom(false);
        // 设置是否显示表格颜色,矩形之间的空隙
        barChart.setDrawGridBackground(false);
        // 设置表格的的颜色，矩形之间的空隙颜色
        barChart.setGridBackgroundColor(Color.GRAY);
        //设置比例显示
        Legend l = barChart.getLegend();
        //设置比例显示在柱形图哪个位置
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        //设置比例显示形状，方形，圆形，线性
        l.setForm(Legend.LegendForm.SQUARE);
        //设置比例显示形状的大小
        l.setFormSize(15f);
        //设置比例显示文字的大小
        l.setTextSize(15f);
        l.setXEntrySpace(4f);
        //设置X轴方向上的属性
        XAxis xAxis = barChart.getXAxis();
        //设置标签显示在柱形图的上方还是下方
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTypeface(Typeface.DEFAULT);
        //设置是否绘制表格
        xAxis.setDrawGridLines(false);
        //设置x标签的间隙
        xAxis.setSpaceBetweenLabels(2);
        //设置柱形图左边y轴方向上的属性
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(Typeface.DEFAULT);
        //设置y轴上的标签数,boolean值为true代表必须8个
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(new DefaultYAxisValueFormatter(0));
        //设置标签在柱形图的哪个位置
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置y轴标签之间的间距
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        //设置柱形图右边y轴方向上的属性,属性含义与上面相同
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(Typeface.DEFAULT);
        rightAxis.setLabelCount(5, true);
        rightAxis.setValueFormatter(new DefaultYAxisValueFormatter(0));
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        // 隐藏右边的坐标轴
        //        barChart.getAxisRight().setEnabled(false);
        // 隐藏左边的坐标轴(同上)
        //        barChart.getAxisLeft().setEnabled(false);
        setData(15);
    }

    /**
     * 绑定数据
     *
     * @param count x轴上的标签数
     */
    private void setData(int count){
        //设置x轴方向上的标签数据
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            xVals.add(i + "");
        }
        //设置每个矩形在y轴上的值
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for(int i = 0; i < count; i++){
            yVals1.add(new BarEntry(20 * i, i));
        }
        //第一个参数是各个矩形在y轴方向上的值得集合，第二个参数为比例的文字说明
        BarDataSet set1 = new BarDataSet(yVals1, "不同颜色代表不同的值");
        //设置矩形之间的间距，参数为百分数，可控制矩形的宽度
        set1.setBarSpacePercent(10f);
        //设置矩形的颜色
        int colors[] = {0xffff0000, 0xff00ff00, 0xff0000ff};
        set1.setColors(colors);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        //设置柱形图的数据
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(Typeface.DEFAULT);
        barChart.setData(data);
    }
}
