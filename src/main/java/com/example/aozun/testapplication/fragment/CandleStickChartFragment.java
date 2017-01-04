package com.example.aozun.testapplication.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aozun.testapplication.R;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/1/4.
 */
public class CandleStickChartFragment extends Fragment{
    private CandleStickChart candleStickChart;
    private View view;
    private LineData lineData;
    private CandleData candleData;
    private ArrayList<String> xVals=new ArrayList<>();
    private List<CandleEntry> candleEntries=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.candlestick_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        candleStickChart = (CandleStickChart) view.findViewById(R.id.CandleStickChart_f);
        candleStickChart.setData(generateCandleData());
    }

    //构建CandleData
    private CandleData generateCandleData(){
        for(int i = 0; i < 8; i++){
            xVals.add("X:"+i);
        }
        candleEntries=getCandleEntries();
        CandleDataSet set = new CandleDataSet(candleEntries, "烛形图");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowWidth(2.0f);
        set.setDecreasingColor(Color.RED);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setIncreasingColor(Color.BLUE);
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setShadowColorSameAsCandle(true);
        set.setHighlightLineWidth(1f);
        set.setHighLightColor(Color.YELLOW);
        set.setDrawValues(false);
        CandleData candleData = new CandleData(xVals,set);
        candleData.addDataSet(set);
        return candleData;
    }

    //指定数据、线颜色、标签生成LineDataSet
    private LineDataSet generateLineDataSet(List<Entry> entries, int color, String label){
        LineDataSet set = new LineDataSet(entries, label);
        set.setColor(color);
        set.setLineWidth(1f);
        set.setDrawCubic(true);//圆滑曲线
        set.setDrawCircles(false);
        set.setDrawCircleHole(false);
        set.setDrawValues(false);
        set.setHighlightEnabled(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return set;
    }
    //构建LineData：MA5\MA10\MA20三条折线图对应不同的数据和折线颜色（这些通过LineDataSet指定），
    // 所以传入一个LineDataSet数组来构建LineData
    private LineData generateMultiLineData(LineDataSet... lineDataSets) {
        List<LineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < lineDataSets.length; i++) {
            dataSets.add(lineDataSets[i]);
        }
        LineData data = new LineData(xVals, dataSets);

        return data;
    }


    //随机数据
    private List<CandleEntry> getCandleEntries(){
        List<CandleEntry> list=new ArrayList<>();
        for(int i = 0; i < 8; i++){
            CandleEntry candleEntry=new CandleEntry(
                    i,(float)(Math.random()),(float)(Math.random()),(float)(Math.random()),(float)(Math.random())
            );
            list.add(candleEntry);
        }

        return list;
    }


    /**
     * 返回烛形图 数据
     * @return
     */
   /* public CandleData getCandleData(){
        // 股票数据
        CandleStock mStock=AssetsManger.
                readAssertToObject("json/marketcandle.json", CandleStock.class,this);
        ArrayList<CandleEntry> candleEntrys=new ArrayList<>();
        for (int i=0;i<mStock.getData().size();i++){

            CandleStock.DataBean databean=mStock.getData().get(i);

            CandleEntry entry=new CandleEntry(i,
                    (float) databean.getHigh(),
                    (float)databean.getLow(),
                    (float)databean.getOpen(),
                    (float)databean.getNow());
            candleEntrys.add(entry);

        }

        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

        for (int i = 0; i < 20; i++) {
            //float mult = (mSeekBarY.get + 1);
            float val = (float) (Math.random() * 40) + 20;

            float high = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;

            boolean even = i % 2 == 0;

            yVals1.add(new CandleEntry(i, val + high, val - low, even ? val + open : val - open,
                    even ? val - close : val + close));
        }



        CandleDataSet candledataset=new   CandleDataSet(yVals1,mStock.getName());     candledataset.setAxisDependency(YAxis.AxisDependency.LEFT);

        candledataset.setBarSpace(1f);
        candledataset.setDecreasingColor(Color.BLUE);
        candledataset.setShadowWidth(12f);
        candledataset.setShowCandleBar(true);

        candledataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        //candledataset.setShadowColor(Color.BLACK);
        candledataset.setShadowColorSameAsCandle(true);
        candledataset.setShadowWidth(0.7f);
        candledataset.setDecreasingColor(Color.GREEN);
        candledataset.setDecreasingPaintStyle(Paint.Style.FILL);
        candledataset.setIncreasingColor(Color.RED);
        candledataset.setIncreasingPaintStyle(Paint.Style.FILL);


        candledataset.setColors(ColorTemplate.LIBERTY_COLORS);


        CandleData candleData=new CandleData();
        candleData.addDataSet(candledataset);

        return  candleData;


    }*/
}
