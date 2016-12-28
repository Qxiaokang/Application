package com.example.aozun.testapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @title 卡片系列的适配器
 * @author robinchen
 *
 */
public class ImageAdapterSerial extends BaseAdapter {
	private Context context;
	private String[] serialName;
	private int[] images;
	private double layoutHeight;
	private WeakHashMap<Integer,Bitmap> mBitmaps =new  WeakHashMap<Integer,Bitmap>();
	public ImageAdapterSerial(Context context, int[] images, String[] serialName, double layoutHeight) {
		this.context = context;
		this.images = images;
		this.serialName = serialName;
		this.layoutHeight = layoutHeight;
	}
	public void clearImage(){
		if(mBitmaps==null || mBitmaps.size()<1)
			return;
		Set<Integer> key = mBitmaps.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();) {
        	Integer pos = (Integer) it.next();
        	if(mBitmaps!=null && mBitmaps.get(pos)!=null && !mBitmaps.get(pos).isRecycled()){
        		mBitmaps.get(pos).recycle();
        	}
				
        }
        mBitmaps.clear();
	}
	public int getCount() {
		return images.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		InputStream inputPhoto=null;
		try{
			ImageView imageView=null;
			if(convertView!=null)
				imageView=(ImageView) convertView;
			else
				imageView=	new ImageView(context);
			 
			 //创建BitMap对象，用于显示图片   
			 //byte[] b = Base64.decode(images[position], Base64.DEFAULT);
			
//			 inputPhoto=new FileInputStream(images[position]);
//			 Bitmap bitmap = BitmapFactory.decodeStream(inputPhoto);
//			 inputPhoto.close();
			/* 矩阵，用于图片比例缩放   */
			Bitmap bitmap = null;
			if(mBitmaps!=null && mBitmaps.get(position)!=null ){
				bitmap=mBitmaps.get(position);
				imageView.setTag(position);
				imageView.setImageBitmap(bitmap);
			}
			else {
				bitmap=BitmapFactory.decodeResource(context.getResources(),images[position]);
				 Matrix matrix = new Matrix();  
				 if(bitmap.getWidth()>bitmap.getHeight()){
					 matrix.postScale((float)243/bitmap.getWidth(),(float)153/bitmap.getHeight());  
				 }else{
					 matrix.postScale((float)153/bitmap.getWidth(),(float)243/bitmap.getHeight());  
				 }
				 //图片不能超出屏幕范围，否则报错，这里进行缩小
				 Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
						 bitmap.getHeight(), matrix, true);
				 bitmap.recycle();
				 newBmp = createReflectedImage(newBmp,position);
				 
				 imageView.setTag(position);
				 imageView.setImageBitmap(newBmp);
				 mBitmaps.put(position,newBmp);
			}
			 imageView.setLayoutParams(new Gallery.LayoutParams((int)(1.3*layoutHeight*243/153/4), (int)(1.3*(layoutHeight-10)/4)));
			 return imageView;
		}catch(Exception ex){
			return null;
		}
	}
	
	/**  添加倒影，原理，先翻转图片，由上到下放大透明度    */
	public Bitmap createReflectedImage(Bitmap originalImage,int position) {
		final int reflectionGap = 3;  // 倒影图和原图之间的间隙

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		/**  翻转图片  */
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		/**  截取翻转后的图片的一半作为倒影   */
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		/**  创建一个Bitmap与倒影图的宽度一致  */
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		
		Paint defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		/**  创建一个画布，高度是原图+间隙+倒影图  */
		Canvas canvas = new Canvas(bitmapWithReflection);
		// 绘制原始图
		canvas.drawBitmap(roundCornerBitmap(originalImage), 0, 0, defaultPaint);
		// 绘制倒影图和原图之间的间隙
		canvas.drawRect(4, height, width-4, height + reflectionGap, defaultPaint);
		// 绘制倒影图
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, defaultPaint);
		

		/** 为倒影图创建一个梯形的着色器  */
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		LinearGradient shader = new LinearGradient(0,originalImage.getHeight(), 0, 
				bitmapWithReflection.getHeight()+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		/**  设置着色器   */
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()+ reflectionGap, paint);
		
		/**  写上系列名 */   //(left, top, right, bottom, paint)
		defaultPaint.setTextAlign(Align.CENTER);
		defaultPaint.setTextSize(20);
		defaultPaint.setColor(Color.BLACK);
		//defaultPaint.setFakeBoldText(true);
		canvas.drawText(serialName[position], width/2, height+reflectionImage.getHeight()*0.9f, defaultPaint);
		originalImage.recycle();
		reflectionImage.recycle();
		return bitmapWithReflection;
	}
	
	/**  圆角图片   */
	public Bitmap roundCornerBitmap(Bitmap bitmap){
		try {  
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);  
			Canvas canvas = new Canvas(output);                  
			final Paint paint = new Paint();  
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),  
			bitmap.getHeight());         
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),  
			bitmap.getHeight()));  
			final float roundPx = 8;  
			paint.setAntiAlias(true);  
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);         
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));              
			final Rect src = new Rect(0, 0, bitmap.getWidth(),bitmap.getHeight());  
			canvas.drawBitmap(bitmap, src, rect, paint);     
			return output;  
		} catch (Exception e) {          
			return bitmap;  
		}  
	}

}
