package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;

import java.util.List;

/**
 * Created by Admin on 2017/7/19.
 */
public class ListBtAdapter extends BaseAdapter{
    private List<?> lists;
    private Context context;
    private int position=-1;
    private int nextPosition=-1;
    private Handler handler;
    public ListBtAdapter(){

    }
    public ListBtAdapter(Context context, List<?> lists, Handler handler){
        this.lists=lists;
        this.context=context;
        this.handler=handler;
    }
    @Override
    public int getCount(){
        return lists==null?0:lists.size();
    }

    @Override
    public Object getItem(int i){
        return lists.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        Holder holder=null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_item_bt,null);
            holder=new Holder();
            holder.tvSelect= (TextView) view.findViewById(R.id.tv_photo);
            view.setTag(holder);
        }
        holder= (Holder) view.getTag();
        holder.tvSelect.setText(lists.get(i).toString());
        if(position==i){
            holder.tvSelect.setSelected(true);
        }else {
            holder.tvSelect.setSelected(false);
        }
        return view;
    }

    class Holder{
         TextView tvSelect;
    }
    //set default selectItem
    public void setDefaultSelectItem(int position){
        this.position=position;
        notifyDataSetChanged();
    }
    //update old position view and new position view
    public void updataView(int posi,int lastposition, ListView listView) {
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {//select position
            View view = listView.getChildAt(posi - visibleFirstPosi);
            Holder holder = (Holder) view.getTag();
            holder.tvSelect.setSelected(true);
        }
        if (lastposition >= visibleFirstPosi && lastposition <= visibleLastPosi) {//set old postion selected false
            View view = listView.getChildAt(lastposition - visibleFirstPosi);
            Holder holder = (Holder) view.getTag();
            holder.tvSelect.setSelected(false);
        }
        this.position=posi;
        handler.sendEmptyMessage(100);
        LogUtils.i("send---100");
    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        handler.sendEmptyMessage(100);
        LogUtils.i("send---100");
    }
}
