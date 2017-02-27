package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.aozun.testapplication.utils.LogUtils;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/2/27.
 */
public class WelcomPageAdapter extends PagerAdapter{
    private List<ImageView> images;
    private Context context;
    private int screenw,screenh,changeTime;
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;
    private Resources resource;
    public WelcomPageAdapter(Context context,List<ImageView> images,int screenw,int screenh,Resources resource
    ,int changeTime){
        this.context=context;
        this.images=images;
        this.screenw=screenw;
        this.screenh=screenh;
        this.resource=resource;
        this.changeTime=changeTime;
        initAnimation();

    }

    private void initAnimation(){
        scaleAnimation=new ScaleAnimation(0,screenw,0,screenh);
        scaleAnimation.setDuration(changeTime);
        alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(changeTime);
    }

    @Override
    public int getCount(){
        return (images==null||images.size()==0)?0:images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position){
        view.addView(images.get(position));
        LogUtils.d("positon:"+position+);
        switch(position){
            case 0:
                images.get(position).setAnimation(scaleAnimation);
                scaleAnimation.startNow();
                break;
            case 1:

                break;

            case 2:

                break;
            case 3:

                break;
        }

        view.addView(images.get(position));
        return images.get(position);
    }



    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
}
