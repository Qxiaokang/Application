package com.example.aozun.testapplication.utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.aozun.testapplication.adapter.RenRenImgAdapter;
import com.example.aozun.testapplication.bean.CardConfig;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/1/16.介绍：人人影视效果的Callback
 */


public class RenRenCallback extends ItemTouchHelper.SimpleCallback{

    protected RecyclerView mRv;
    protected List<Integer> mDatas;
    protected RenRenImgAdapter mAdapter;

    public RenRenCallback(RecyclerView rv, RenRenImgAdapter adapter, List<Integer> datas){
        this(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, rv, adapter, datas);
    }

    public RenRenCallback(int dragDirs, int swipeDirs, RecyclerView rv, RenRenImgAdapter adapter, List<Integer> datas){
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder){
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return mRv.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        //Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
        //rollBack(viewHolder);
        //★实现循环的要点
        LogUtils.d("data10:"+ mDatas.get(9));
        int remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        int [] ints=new int[mDatas.size()];
        for(int i = 0; i < mDatas.size();i++){
            ints[i]=mDatas.get(i);
        }
        mAdapter.IMAGES=ints;
        LogUtils.d("移除后data长度："+mDatas.size()+"data10:"+mDatas.get(9));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //Log.e("swipecard", "onChildDraw()  viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");
        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if(fraction > 1){
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if(level > 0){
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                if(level < CardConfig.MAX_SHOW_COUNT - 1){
                    child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                }else{
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }
    }
}
