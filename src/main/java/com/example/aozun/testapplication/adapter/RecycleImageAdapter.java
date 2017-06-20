package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.ImageMessage;
import com.example.aozun.testapplication.utils.LogUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/3/6.
 * recycleview图片的适配器
 */
public class RecycleImageAdapter extends RecyclerView.Adapter<RecycleImageAdapter.ImageHoder>{
    private Context context = null;
    private List<Bitmap> bitmaps = null;
    private List<?> stringList = null;
    private boolean isflex = false;

    public RecycleImageAdapter(){
    }

    public RecycleImageAdapter(Context context, List<Bitmap> bitmaps){
        this.context = context;
        this.bitmaps = bitmaps;
    }

    public RecycleImageAdapter(Context context, List<?> urls, boolean isflex){
        this.context = context;
        this.stringList = urls;
        this.isflex = isflex;
    }

    @Override
    public ImageHoder onCreateViewHolder(ViewGroup parent, int viewType){
        ImageHoder imageHoder = new ImageHoder(LayoutInflater.from(context).inflate(R.layout.recycle_image_layout, parent, false));
        return imageHoder;
    }

    @Override
    public void onBindViewHolder(ImageHoder holder, int position){
        LogUtils.e("position:" + position + "   isflex:" + isflex);
        if(isflex == false){
            holder.imageView.setImageBitmap(bitmaps.get(position));
        }else{
            Picasso.with(context).load(((ImageMessage)stringList.get(position)).getPicSmall()).config(Bitmap.Config.ARGB_8888).placeholder(R.drawable.normal).error(R.drawable.normal).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount(){
        if(isflex == false){
            return bitmaps == null ? 0 : bitmaps.size();
        }else{
            return stringList == null ? 0 : stringList.size();
        }
    }

    class ImageHoder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ImageHoder(View itemView){
            super(itemView);
            LogUtils.e("hoder");
            imageView = (ImageView) itemView.findViewById(R.id.recycle_image);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            if(layoutParams instanceof FlexboxLayoutManager.LayoutParams){
                FlexboxLayoutManager.LayoutParams lp = (FlexboxLayoutManager.LayoutParams) layoutParams;
                lp.setFlexGrow(0f);
            }
        }
    }
}
