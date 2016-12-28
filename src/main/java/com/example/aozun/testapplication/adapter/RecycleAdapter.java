package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aozun.testapplication.R;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2016/11/16.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Butviehoder> implements View.OnClickListener, View.OnLongClickListener{

    private RecycleonclickListener recycleonclickListener;
    private List<String> strings;
    private Context context;
    private RecycleonLongclickListener longclickListener;
    public RecycleAdapter(Context context, List<String>  strings){
        this.context = context;
        this.strings = strings;
    }

    public RecycleAdapter(){
    }

    @Override
    public Butviehoder onCreateViewHolder(ViewGroup parent, int viewType){
        Butviehoder butviehoder = new Butviehoder(LayoutInflater.from(context).inflate(R.layout.recycle_layout, parent, false));
        return butviehoder;
    }

    @Override
    public void onBindViewHolder(Butviehoder holder, int position){

        holder.button.setText(strings.get(position));
        holder.button.setTag(strings.get(position));
        holder.button.setOnClickListener(this);
        holder.button.setOnLongClickListener(this);
    }

    @Override
    public int getItemCount(){
        return strings.size();
    }

    @Override
    public void onClick(View v){
        recycleonclickListener.onitemListener(v, (String) v.getTag());
    }

    @Override
    public boolean onLongClick(View v){
        longclickListener.onLongItemListener(v, (String) v.getTag());
        return true;
    }

    class Butviehoder extends RecyclerView.ViewHolder{

        private final Button button;

        public Butviehoder(View itemView){
            super(itemView);
            button = (Button) itemView.findViewById(R.id.recybt);
        }
    }

    //回调接口
    public interface RecycleonclickListener{
        void onitemListener(View view, String data);
    }


    public void setOnItemclickListener(RecycleonclickListener recycleonclickListener){
        this.recycleonclickListener=recycleonclickListener;
    }

    //长按回调接口
    public interface RecycleonLongclickListener{
        void onLongItemListener(View v,String data);
    }

    public void setOnlongItemclickListener(RecycleonLongclickListener longItemclickListener){
        this.longclickListener=longItemclickListener;
    }
}
