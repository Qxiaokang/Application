package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * Created by HHD-H-I-0369 on 2017/1/16.
 */

public class RenRenImgAdapter extends RecyclerView.Adapter<RenRenImgAdapter.ImgViewHolder>{
    @DrawableRes
    public static int[] IMAGES = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,};
    private final Context mContext;

    public class ImgViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        public ImgViewHolder(ImageView imageView){
            super(imageView);
            mImageView = imageView;
        }
    }

    public RenRenImgAdapter(Context context){
        mContext = context;
    }

    @Override
    public ImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(660, 1000);
        lp.leftMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        lp.rightMargin = 5;
        imageView.setLayoutParams(lp);
        return new ImgViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ImgViewHolder holder, int position){
        holder.mImageView.setImageResource(IMAGES[position % IMAGES.length]);
        LogUtils.d("setImageResource:"+IMAGES[position % IMAGES.length]+"   IMAGES[position % IMAGES.length]:"+position%IMAGES.length);
    }

    @Override
    public int getItemCount(){
        return IMAGES.length ;
    }
}

