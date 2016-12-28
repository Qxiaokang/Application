package com.example.aozun.testapplication.views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 自定义画廊效果
 * Created by HHD-H-I-0369 on 2016/11/22.
 */
public class HrglView extends Gallery{
    private int mCoveflowCenter;
    private Camera mCamera = new Camera();
    private int mMaxRotationAngle = 60;  // 倾斜的角度

    public int getmMaxZoom(){
        return mMaxZoom;
    }

    public void setmMaxZoom(int mMaxZoom){
        this.mMaxZoom = mMaxZoom;
    }

    public boolean ismAlphaMode(){
        return mAlphaMode;
    }

    public void setmAlphaMode(boolean mAlphaMode){
        this.mAlphaMode = mAlphaMode;
    }

    public boolean ismCircleMode(){
        return mCircleMode;
    }

    public void setmCircleMode(boolean mCircleMode){
        this.mCircleMode = mCircleMode;
    }

    public int getmMaxRotationAngle(){
        return mMaxRotationAngle;
    }

    public void setmMaxRotationAngle(int mMaxRotationAngle){
        this.mMaxRotationAngle = mMaxRotationAngle;
    }

    private int mMaxZoom = -500;  // z轴

    private boolean mAlphaMode = true;
    private boolean mCircleMode = false;
    private int FLINGTHRESHOLD;

    public HrglView(Context context){
        super(context);
        this.setStaticTransformationsEnabled(true);
        float scale=getResources().getDisplayMetrics().density;//密度
        FLINGTHRESHOLD = (int) (20.0f * scale + 0.5f);
    }

    public HrglView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
        float scale=getResources().getDisplayMetrics().density;
        FLINGTHRESHOLD= (int) (20.0f*scale+0.5f);
    }

    public HrglView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.setStaticTransformationsEnabled(true);
        float scale=getResources().getDisplayMetrics().density;
        FLINGTHRESHOLD= (int) (20.0f*scale+0.5f);
    }

    public static int getCenterofView(View view){

        return view.getLeft()+view.getWidth()/2;
    }
    public int getCenterofCoverFlow(){

        return (getWidth()-getPaddingLeft()-getPaddingRight())/2+getPaddingLeft();
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t){
        int childCenter=getCenterofView(child);
        int childWidth=child.getWidth();
        int rotationAngle=0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if(childWidth==childCenter){

            transformImageBitmap((ImageView) child,t,0,0);
        }else{
            rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
            if (Math.abs(rotationAngle) > mMaxRotationAngle) {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle: mMaxRotationAngle;
            }
            transformImageBitmap((ImageView) child, t, rotationAngle,
                    (int) Math.floor((mCoveflowCenter - childCenter)/ (childWidth==0?1:childWidth)));

        }
        return true;
    }
    //横竖屏转换时调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        mCoveflowCenter=getCenterofCoverFlow();
        super.onSizeChanged(w, h, oldw, oldh);
    }


    /**  滑动时，图片的切换
     Transform the Image Bitmap by the Angle passed
     @param / ImageView the ImageView whose bitmap we want to rotate
     @param t  transformation
     @param rotationAngle   the Angle by which to rotate the Bitmap  */
    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle, int d) {
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs(rotationAngle);
        mCamera.translate(0.0f, 0.0f, 100.0f);
        // As the angle of the view gets less, zoom in
        if (rotation <= mMaxRotationAngle) {
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
            mCamera.translate(0.0f, 0.0f, zoomAmount);
            if (mCircleMode) {
                if (rotation < 40)
                    mCamera.translate(0.0f, 155, 0.0f);
                else
                    mCamera.translate(0.0f, (255 - rotation * 2.5f), 0.0f);
            }
            if (mAlphaMode) {
                ((ImageView) (child)).setAlpha((int) (255 - rotation * 2.5));
            }
        }
        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);

        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        // cap the velocityX to scroll only one page
        if (velocityX > FLINGTHRESHOLD) {
            return super.onFling(e1, e2, 450, velocityY);
        } else if (velocityX < -FLINGTHRESHOLD) {
            return super.onFling(e1, e2, -450, velocityY);
        } else {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
