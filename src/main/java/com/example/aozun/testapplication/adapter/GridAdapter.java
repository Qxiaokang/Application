package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2016/12/30.
 */
public class GridAdapter extends BaseAdapter{
    private List<Bitmap>  bitmaps;
    private Context context;
    public GridAdapter(Context context,List<Bitmap> bitmaps){
        this.context=context;
        this.bitmaps=bitmaps;
    }

    @Override
    public int getCount(){
        return bitmaps==null?0:bitmaps.size();
    }

    @Override
    public Object getItem(int i){
        return bitmaps.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        Holder holder;
        LogUtils.e("bitmapsize:"+bitmaps.size());
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.grid_image,null);
            holder=new Holder();
            holder.imageView= (ImageView) view.findViewById(R.id.grid_iv);

        }else{
            holder= (Holder) view.getTag();
        }
        holder.imageView.setImageBitmap(bitmaps.get(i));
        view.setTag(holder);
        return view;
    }

    class Holder{
        private ImageView imageView;
    }
}
