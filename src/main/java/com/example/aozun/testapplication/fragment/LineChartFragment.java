package com.example.aozun.testapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aozun.testapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Created by HHD-H-I-0369 on 2017/1/4.
 */
public class LineChartFragment extends Fragment{
    private View view;
    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.linechart_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        lineChart = (LineChart) view.findViewById(R.id.line_f);
        LineData lineData=makeLineData(8);//
        setChartStyle(lineChart,lineData,Color.GRAY);
    }


    /**
     *  数据点
     */
    private LineData makeLineData(int count) {

        ArrayList<String> x = new ArrayList<String>();

        // 添加 x 轴显示的数据
        for (int i = 0; i < count; i++) {
            x.add("x:" + i);
        }

        // 随机添加 y 轴显示的数据
        ArrayList<Entry> y = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float values = (float)(Math.random() * 100);
            Entry entry = new Entry(values, i);
            y.add(entry);
        }

        // y 轴的所有数据
        LineDataSet mLineDataSet = new LineDataSet(y,"测试" );//"测试数据的作者：antimage08"


        // ==================== 用 y 轴的集合来设置参数 ====================
        // 线宽
        mLineDataSet.setLineWidth(3.0f);

        // 折线的颜色
        mLineDataSet.setColor(Color.RED);

        // 显示的圆形的大小
        mLineDataSet.setCircleSize(5.0f);

        // 圆球的颜色
        mLineDataSet.setCircleColor(Color.GREEN);

        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(true);

        // 点击后，十字交叉线的颜色
        mLineDataSet.setHighLightColor(Color.CYAN);

        // 设置这项上显示的数据点的字体大小
        mLineDataSet.setValueTextSize(10.0f);

        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线。
        // mLineDataSet.setDrawCubic(true);
        // 默认是直线
        // 曲线的平滑度，值越大越平滑。
        // mLineDataSet.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
        // mLineDataSet.setDrawFilled(true);
        // mLineDataSet.setFillAlpha(128);
        // mLineDataSet.setFillColor(Color.RED);

        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet.setCircleColorHole(Color.YELLOW);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        mLineDataSet.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                int num = (int)value;
                return "y:" + num;
            }
        });


        LineData mLineData = new LineData(x, mLineDataSet);
        return mLineData;
    }

    // 设置 chart 显示的样式（8个点）
    private void setChartStyle(LineChart chart, LineData mLineData, int color) {

        // 是否在折线图上添加边框
        chart.setDrawBorders(false);

        chart.setDescription("@xiaokang");

        // 没有数据时显示，类似于 ListView 的 EmptyView
        chart.setNoDataTextDescription("如果传给MPAndroidChart的数据为空，那么你将看到这段文字。CrazyBoy");

        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.BLUE)将失效;
        chart.setDrawGridBackground(false);
        chart.setGridBackgroundColor(Color.BLUE);

        // 设置能否触摸
        chart.setTouchEnabled(true);

        // 设置能否拖动
        chart.setDragEnabled(true);

        // 设置能否缩放
        chart.setScaleEnabled(true);

        // 设置能否在屏幕上做多指手势
        chart.setPinchZoom(true);

        // 设置背景色
        chart.setBackgroundColor(color);

        // 设置 x, y轴上的数据
        chart.setData(mLineData);

        // 沿 x 轴运动 2000毫秒,也就是绘制完所有的数据所需要的时间
        chart.animateX(2000);

        // ==================== 以下是 LineChart 的设置 ====================

        // 设置LineChart的标示，每一组的 y 的 value 值
        Legend mLegend = chart.getLegend();

        // 设置 LineChart 所显示的位置
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        // 设置显示的样式，此处为矩形，后文中 "测试数据的作者：antimage08" 前面的图标
        mLegend.setForm(Legend.LegendForm.SQUARE);

        // 设置字体大小 ，后文中 "测试数据的作者：antimage08" 前面的图标的大小
        mLegend.setFormSize(15.0f);

        // 设置颜色
        mLegend.setTextColor(Color.RED);

    }
}
