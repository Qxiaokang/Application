package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.bean.Image;
import com.example.aozun.testapplication.bean.ImageMessage;
import com.example.aozun.testapplication.utils.LogUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by HHD-H-I-0369 on 2017/3/6.
 * recycleview图片的适配器
 */
public class RecycleImageAdapter extends RecyclerView.Adapter<RecycleImageAdapter.ImageHoder> implements View.OnClickListener, View.OnLongClickListener{
    private Context context = null;
    private List<?> bitmaps = null;
    private List<?> stringList = null;
    private boolean isflex = false;
    private Handler handler=null;
    public ItemListener itemListener;
    public ItemLongListener itemLongListener;
    public RecycleImageAdapter(){
    }

    public RecycleImageAdapter(Context context, List<?> bitmaps, Handler mHandler){
        this(context,bitmaps,false,mHandler);
    }
    public RecycleImageAdapter(Context context, List<?> urls, boolean isflex,Handler mhandler){
        this.context = context;
        this.stringList = urls;
        this.bitmaps=urls;
        this.isflex = isflex;
        this.handler=mhandler;
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
            Image image= (Image) bitmaps.get(position);
            holder.imageView.setImageBitmap(image.getBitmap());
            holder.checkBox.setChecked(image.isChecked());
            LogUtils.i("ischecked:"+image.isChecked());
            if(image.isUpload()){
               holder.checkBox.setVisibility(View.INVISIBLE);
            }
            //set image createTime
            holder.tvTime.setText("修改时间： "+image.getCreateTime());
            if(position==0){
                handler.sendEmptyMessage(101);
            }
        }else{
            Picasso.with(context).load(((ImageMessage)stringList.get(position)).getPicSmall()).config(Bitmap.Config.ARGB_8888).placeholder(R.drawable.normal).error(R.drawable.normal).into(holder.imageView);
        }
        holder.checkBox.setTag(holder.getAdapterPosition());
        holder.imageView.setTag(holder.getAdapterPosition());
        holder.checkBox.setOnClickListener(this);
        holder.imageView.setOnClickListener(this);
    }

    @Override
    public int getItemCount(){
        if(isflex == false){
            return bitmaps == null ? 0 : bitmaps.size();
        }else{
            return stringList == null ? 0 : stringList.size();
        }
    }

    @Override
    public void onClick(View view){
        itemListener.onImageChecked(view, (Integer) view.getTag());
    }

    @Override
    public boolean onLongClick(View view){
        itemLongListener.setItemOnlongPress(view, (Integer) view.getTag());
        return true;
    }

    class ImageHoder extends RecyclerView.ViewHolder{
        ImageView imageView;CheckBox checkBox;
        TextView tvTime;
        public ImageHoder(View itemView){
            super(itemView);
            LogUtils.e("hoder");
            imageView = (ImageView) itemView.findViewById(R.id.recycle_image);
            checkBox= (CheckBox) itemView.findViewById(R.id.iv_check);
            tvTime= (TextView) itemView.findViewById(R.id.tv_createtime);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            if(layoutParams instanceof FlexboxLayoutManager.LayoutParams){
                FlexboxLayoutManager.LayoutParams lp = (FlexboxLayoutManager.LayoutParams) layoutParams;
                lp.setFlexGrow(0f);
            }
        }
    }

    public void updateBitmaps(List<Image> bitmaps,boolean isAll){
        this.bitmaps=bitmaps;
        if(isAll){
            notifyDataSetChanged();
        }
    }


    public interface ItemListener{
        void onImageChecked(View view,int tag);
    }

    public void setImgeCheckListener(ItemListener itemListener){
        this.itemListener=itemListener;
    }
    public List<Bitmap> getDatas(){
        if(bitmaps!=null){
            return (List<Bitmap>) bitmaps;
        }else {
            return null;
        }

    }


    public interface ItemLongListener{
        void setItemOnlongPress(View view,int tag);
    }
    public void setItemOnLongListener(ItemLongListener itemOnLongListener){
        this.itemLongListener=itemOnLongListener;
    }

}
