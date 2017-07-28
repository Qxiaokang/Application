package com.example.aozun.testapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.adapter.ListBtAdapter;
import com.example.aozun.testapplication.adapter.RecycleImageAdapter;
import com.example.aozun.testapplication.bean.Image;
import com.example.aozun.testapplication.utils.ComUtil;
import com.example.aozun.testapplication.utils.InitContent;
import com.example.aozun.testapplication.utils.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xk
 *
* */
public class PhotoActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener, RecycleImageAdapter.ItemListener, RecycleImageAdapter.ItemLongListener{
    private static final String[] strings = new String[]{"IDCard", "SocialSecurity", "Fund", "Personage", "Other1", "Other2", "Other3", "Other4"
            , "Other5","Other6","Other7","Other8","Other9","Other10"};
    private RecyclerView photoRecycle = null;
    private RecycleImageAdapter recycleImageAdapter = null;
    private ListView listBts = null;
    private ListBtAdapter listBtAdapter = null;
    private int defaultPosition = 0, position = 0;
    private List<Image> images = new ArrayList<>();
    private static String path = null;
    private Button btTakePhotos = null;
    private Dialog dialog=null;
    private ImageView animImg=null;
    private AnimationDrawable animationDrawable=null;
    private int checkTag=1;
    private int fromPosition=0,toPosition=0;
    private Map<String,String> mapTimes=new HashMap<>();
    private boolean isFirst=true;
    private GridLayoutManager manager=null;
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initViews();
    }

    private void initViews(){
        btTakePhotos = (Button) findViewById(R.id.bt_takephotos);
        path = InitContent.getInstance().getPhotoPath();
        photoRecycle = (RecyclerView) findViewById(R.id.rv_photo);
        listBts = (ListView) findViewById(R.id.lv_photo);
        animImg= (ImageView) findViewById(R.id.iv_dia_load);
        animationDrawable= (AnimationDrawable) animImg.getDrawable();
        animImg.setVisibility(View.VISIBLE);
        animationDrawable.start();
        manager = new GridLayoutManager(this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        photoRecycle.setLayoutManager(manager);
        photoRecycle.setItemAnimator(new DefaultItemAnimator());
        listBtAdapter = new ListBtAdapter(this, Arrays.asList(strings),mHandler);
        listBts.setAdapter(listBtAdapter);
        listBts.setOnItemClickListener(this);
        listBtAdapter.setDefaultSelectItem(defaultPosition);
        this.position = defaultPosition;
        recycleImageAdapter = new RecycleImageAdapter(this, images,mHandler);
        photoRecycle.addItemDecoration(new SpaceItemDecoration(50));
        photoRecycle.setAdapter(recycleImageAdapter);
        btTakePhotos.setOnClickListener(this);
        recycleImageAdapter.setImgeCheckListener(this);
        recycleImageAdapter.setItemOnLongListener(this);
        itemTouchHelper.attachToRecyclerView(photoRecycle);
        initDialog();
    }
    //refresh RecycleView
    @Override
    protected void onResume(){
        if(!isFirst){
            listBtAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    //init list imagsData
    private List<Image> getListImage(List<?> list){
        images.clear();
        if(list!=null&&list.size()>0){
            for(int i = 0; i <list.size(); i++){
                Image image=new Image();
                image.setPath(path+File.separator+strings[position]);
                image.setType(i);
                image.setCreateTime(mapTimes.get(strings[position]+i));
                image.setPosition(i);
                image.setBitmap((Bitmap) list.get(i));
                images.add(image);
            }
        }
        return images;
    }

    //click item
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        images.clear();
        recycleImageAdapter.notifyDataSetChanged();
        if(position != i){
            listBtAdapter.updataView(i, position, listBts);
        }
        this.position = i;
    }

    //get list bitmap by path
    private List<Bitmap> getBitmaps(String path, String derectory){
        File file = null;
        if(derectory == null || "".endsWith(derectory.trim())){
            file = new File(path);
        }else{
            file = new File(path + File.separator + derectory);
            LogUtils.e("boolean:" + file.exists());
        }
        List<Bitmap> bitmaps = new ArrayList<>();
        if(file.exists()){
            File[] files = file.listFiles();
            for(int i = 0; i < files.length; i++){
                if(files[i].isFile() && files[i].getName().endsWith(".jpg")){
                    Date createDate=new Date(files[i].lastModified());
                    String time=dateFormat.format(createDate);
                    mapTimes.put(derectory+i,time);
                    Bitmap bitmap = getBitmapFromFile(files[i], (screenW-160- ComUtil.dip2Px(100))/2, (int) ((screenW-160- ComUtil.dip2Px(100))/2*0.58));
                    bitmaps.add(bitmap);
                }
            }
        }
        LogUtils.i("bitmaps.Size:" + bitmaps.size());
        return bitmaps;
    }
    //compress bitmap
    public Bitmap getBitmapFromFile(File dst, int width, int height){
        if(null != dst && dst.exists()){
            BitmapFactory.Options opts = null;
            if(width > 0 && height > 0){
                opts = new BitmapFactory.Options();          //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);            //这里一定要将其设置回false，因为之前我们将其设置成了true
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try{
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            }catch(OutOfMemoryError e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels){
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if(initialSize <= 8){
            roundedSize = 1;
            while(roundedSize < initialSize){
                roundedSize <<= 1;
            }
        }else{
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels){
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if(upperBound < lowerBound){
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if((maxNumOfPixels == -1) && (minSideLength == -1)){
            return 1;
        }else if(minSideLength == -1){
            return lowerBound;
        }else{
            return upperBound;
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bt_takephotos:
                CameraActivity.path = path + File.separator + strings[position];
                Intent intent = new Intent(PhotoActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.dialog_cancel:
                if(dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 100:
                    LogUtils.i("handler---receive---100");
                    animImg.setVisibility(View.VISIBLE);
                    isFirst=false;
                    new BitmapThread().start();
                break;
                case 111:
                    LogUtils.i("handler---receive---111");
                    if(images.size()==0){
                        animImg.setVisibility(View.INVISIBLE);
                    }else {
                        recycleImageAdapter.updateBitmaps(images,true);
                    }
                    break;
                case 101:
                    LogUtils.i("handler---receive---101");
                    animImg.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onImageChecked(View view, int tag){
        LogUtils.i("tag:"+tag);
        if(images.get(tag).isChecked()){
            images.get(tag).setChecked(false);
        }else {
            images.get(tag).setChecked(true);
        }
        LogUtils.i("setcheckde:"+images.get(tag).isChecked());
        recycleImageAdapter.notifyItemChanged(tag);
    }

    //long press image
    @Override
    public void setItemOnlongPress(View view, int tag){

    }

    /**private void initLoadingDialog(){
        dialog=new Dialog(this,R.style.Translucent_Notitle);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_loading_layout,null);
        animImg= (ImageView) view.findViewById(R.id.iv_dia_load);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        animationDrawable= (AnimationDrawable) animImg.getDrawable();
    }*/
    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0|| parent.getChildAdapterPosition(view)==1) {
                outRect.top = mSpace;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }


    ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback(){
        //move orientation
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
            //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //允许上下的拖动
            //int dragFlags =ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; //允许左右的拖动
            //int swipeFlags = ItemTouchHelper.LEFT; //只允许从右向左侧滑
            //int swipeFlags = ItemTouchHelper.DOWN; //只允许从上向下侧滑
            //一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值
            //makeMovementFlags(dragFlags, swipeFlags)

            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT; //允许上下左右的拖动
            int swipeFlags = ItemTouchHelper.START| ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
            fromPosition=viewHolder.getAdapterPosition();
            toPosition=target.getAdapterPosition();
            LogUtils.d("fromposition:"+fromPosition+"  toposition:"+toPosition);
            if(fromPosition<toPosition){
                for(int i = fromPosition; i < toPosition; i++){
                    Collections.swap(images,i,i+1);
                }
            }else {
                for(int j = fromPosition; j > toPosition; j--){
                    Collections.swap(images,j,j-1);
                }
            }
            LogUtils.i("frompostion:"+images.get(fromPosition).isChecked()+"    toposition:"+images.get(toPosition).isChecked());
            recycleImageAdapter.notifyItemMoved(fromPosition,toPosition);
            //recycleImageAdapter.notifyItemRangeChanged(fromPosition>toPosition?toPosition:fromPosition,toPosition>fromPosition?toPosition-fromPosition+1:fromPosition-toPosition+1);
           // recycleImageAdapter.updateBitmaps(images,true);
            return true;
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
            if(dialog!=null&&!dialog.isShowing()){
                dialog.show();
            }
        }
        //open longpress drag
        @Override
        public boolean isLongPressDragEnabled(){
            return true;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState){
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.BLUE);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            LogUtils.e("drawOver-----");
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y){
            LogUtils.e("onmoved------");
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        }
    });

    private View dialogView=null;
    private TextView tv_dvCancle=null;
    private void initDialog(){
        dialog=new Dialog(this);
        dialogView= LayoutInflater.from(this).inflate(R.layout.dialog_layout1,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        tv_dvCancle= (TextView) dialogView.findViewById(R.id.dialog_cancel);
        tv_dvCancle.setOnClickListener(this);
    }
    class  BitmapThread extends Thread{
        @Override
        public void run(){
            super.run();
            LogUtils.i("Thread---start");
            if(!animationDrawable.isRunning()){
                animationDrawable.start();
            }
            getListImage(getBitmaps(path, strings[position]));
            SystemClock.sleep(1000);
            //animationDrawable.stop();
            mHandler.sendEmptyMessage(111);
        }
    }

    @Override
    protected void onDestroy(){
        if(animationDrawable!=null&&animationDrawable.isRunning()){
            animationDrawable.stop();
        }
        Thread.interrupted();
        super.onDestroy();
    }
}
