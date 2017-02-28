package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aozun.testapplication.utils.LogUtils;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/2/27.
 * 图片适配器
 */
public class WelcomPageAdapter extends PagerAdapter{
    private List<ImageView> images;
    private Context context;
    private ImageView imageView;
    public WelcomPageAdapter(Context context,List<ImageView> images){
        this.context=context;
        this.images=images;
    }

    @Override
    public int getCount(){
        return (images==null||images.size()==0)?0:images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position){
        imageView=images.get(position);
        view.addView(imageView);
        LogUtils.d("pageadapter--position:"+position);
        return imageView;
    }



    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(images.get(position));
    }
}
