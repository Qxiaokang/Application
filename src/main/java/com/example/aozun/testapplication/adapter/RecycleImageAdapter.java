package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/3/6.
 * recycleview图片的适配器
 */
public class RecycleImageAdapter extends RecyclerView.Adapter<RecycleImageAdapter.ImageHoder>{
    private Context context;
    private List<Bitmap> bitmaps;
    public RecycleImageAdapter(Context context,List<Bitmap> bitmaps){
        this.context=context;
        this.bitmaps=bitmaps;
    }

    @Override
    public ImageHoder onCreateViewHolder(ViewGroup parent, int viewType){
        ImageHoder imageHoder= new ImageHoder(LayoutInflater.from(context).inflate(R.layout.recycle_image_layout,parent,false));

        return imageHoder;
    }

    @Override
    public void onBindViewHolder(ImageHoder holder, int position){

        holder.imageView.setImageBitmap(bitmaps.get(position));
    }

    @Override
    public int getItemCount(){
        return bitmaps==null?0:bitmaps.size();
    }

    class ImageHoder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageHoder(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.recycle_image);

        }
    }
}
