package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.thread.SaveThread;
import com.example.aozun.testapplication.utils.InitContent;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.MainApplication;
import com.example.aozun.testapplication.utils.UniversalUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by HHD-H-I-0369 on 2017/2/28.
 * 相机页面
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener, SurfaceHolder.Callback{
    private Button takephoto, cancleC, changeC, btfocus;
    private String picname;
    private static final int TAKE_PHOTO_CODE = 3;
    private SurfaceView surfaceView;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private int cameraPosition = 0;//1代表前置，0代表后置
    private LinearLayout photoL;//存放照片的linearlayout
    public static String path=InitContent.getInstance().getPhotoPath(), yes = "确定", no = "取消";
    private byte[] photobytes;
    private Bitmap bitmap;
    private View lineview;//分隔线
    private ExecutorService executorService= Executors.newFixedThreadPool(3);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        //设置手机屏幕朝向，一共有7种
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //SCREEN_ORIENTATION_BEHIND： 继承Activity堆栈中当前Activity下面的那个Activity的方向
        //SCREEN_ORIENTATION_LANDSCAPE： 横屏(风景照) ，显示时宽度大于高度
        //SCREEN_ORIENTATION_PORTRAIT： 竖屏 (肖像照) ， 显示时高度大于宽度
        //SCREEN_ORIENTATION_SENSOR  由重力感应器来决定屏幕的朝向,它取决于用户如何持有设备,当设备被旋转时方向会随之在横屏与竖屏之间变化
        //SCREEN_ORIENTATION_NOSENSOR： 忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
        //SCREEN_ORIENTATION_UNSPECIFIED： 未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择
        //SCREEN_ORIENTATION_USER： 用户当前的首选方向
        setContentView(R.layout.activity_camera);
        MainApplication.getInstance().addActivity(this);
        initViews();
        LogUtils.i("camerActivityOcreate()");
    }

    //初始化
    private void initViews(){
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        takephoto = (Button) findViewById(R.id.take);
        cancleC = (Button) findViewById(R.id.bt_cancel);
        changeC = (Button) findViewById(R.id.change_id);
        photoL = (LinearLayout) findViewById(R.id.photo_linear);
        btfocus = (Button) findViewById(R.id.focus_bt);
        lineview = findViewById(R.id.line_view);
        btfocus.setOnClickListener(this);
        takephoto.setOnClickListener(this);
        cancleC.setOnClickListener(this);
        changeC.setOnClickListener(this);
        surfaceView = (SurfaceView) findViewById(R.id.surface_id);
        surfaceHolder = surfaceView.getHolder();//获得句柄
        surfaceHolder.addCallback(this);//添加回调
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//不维护自己的缓冲区，等待屏幕渲染引擎将内容摄推送到用户面前

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            //拍照
            case R.id.take:
                if(camera!=null){
                    camera.takePicture(null, null, jpegpic);
                }
                takephoto.setEnabled(false);
                cancleC.setText(yes);
                changeC.setText(no);
                break;
            //退出
            case R.id.bt_cancel:
                if(bitmap != null){
                    bitmap.recycle();
                    bitmap = null;
                }
                //确认照片则保存
                if(cancleC.getText().toString().equals(yes)){
                    if(path != null && photobytes != null && photobytes.length != 0){
                       //开启线程
                        executorService.execute(new SaveThread(path,photobytes));
                    }
                    startTake();
                    takephoto.setEnabled(true);
                    cancleC.setText("cancle");
                    changeC.setText("change");
                }else{
                    //如果是返回则直接退出
                    CameraActivity.this.finish();
                }
                break;
            //切换摄相头
            case R.id.change_id:
                if(bitmap != null){
                    bitmap.recycle();
                    bitmap = null;
                }
                //如果取消照片则不保存，也不显示
                if(changeC.getText().toString().endsWith(no)){
                    startTake();
                    photoL.removeViewAt(photoL.getChildCount() - 1);//移除
                    takephoto.setEnabled(true);
                    changeC.setText("change");
                    cancleC.setText("cancle");
                }else{
                    int cameracout = 0;
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    cameracout = Camera.getNumberOfCameras();//得到摄相头的个数
                    if(cameracout != 0){
                        for(int i = 0; i < cameracout; i++){
                            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄相头的信息
                            if(cameraPosition == 0){
                                //切换为前置
                                if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                                    changeCamera(i);
                                    cameraPosition = 1;
                                    break;
                                }
                            }else{
                                //前置变为后置
                                if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                                    changeCamera(i);
                                    cameraPosition = 0;
                                    break;
                                }
                            }
                            LogUtils.i("camerainfo.facing:" + cameraInfo.facing);
                        }
                    }else{
                        UniversalUtils.getInstance().showToast(this, "相机启动失败");
                    }
                    break;
                }
                //聚焦
            case R.id.focus_bt:
                camera.autoFocus(null);
                break;
        }
    }

    //切换摄相头的方法
    private void changeCamera(int i){
        camera.stopPreview();//停掉原来的摄相头
        camera.release();//释放资源
        camera = null;//取消摄相头
        camera = Camera.open(i);//打开当前选中的摄相头
        try{
            Camera.Parameters parameters = camera.getParameters();
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                //parameters.setRotation(90);
            }else{
                parameters.set("orientation", "landscape");
                //parameters.setRotation(0);
                camera.setDisplayOrientation(0);
            }
            camera.setPreviewDisplay(surfaceHolder);//显示取景
            camera.startPreview();//开始预览
        }catch(IOException e){
            LogUtils.d("摄相头切换失败");
            e.printStackTrace();
        }
    }

    //调用系统相机拍照
    private void takePhotos(){
        camera.startPreview();
        picname = UniversalUtils.getInstance().getUUID() + ".jpg";
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path + File.separator + picname)));
            startActivityForResult(intent, TAKE_PHOTO_CODE);
        }catch(Exception e){
            UniversalUtils.getInstance().showToast(this, "启用系统相机失败");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0){
            return;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        LogUtils.d("SurfaceCreated----");
        Camera.Size size = null;
        int width = 0, height = 0;
        //当sufaceview创建时开启相机
        if(camera == null){
            camera = Camera.open(cameraPosition);
        }
        try{
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for(int i = 0; i < supportedPreviewSizes.size(); i++){
                size = supportedPreviewSizes.get(i);
                if(i == supportedPreviewSizes.size() - 1){
                    width = size.width;
                    height = size.height;
                }
                LogUtils.i("分辨率：" + size.width + "," + size.height);
            }
            List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
            for(int i = 0; i < supportedPictureSizes.size(); i++){
                LogUtils.i("picsize:" + supportedPictureSizes.get(i).width + "," + supportedPictureSizes.get(i).height);
            }
            parameters.setPreviewSize(1280, 720);//设置像素
            parameters.setPictureSize(1280, 720);//设置图片尺寸
            parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
            parameters.setPictureFormat(PixelFormat.JPEG);
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                //parameters.setRotation(90);
            }else{
                parameters.set("orientation", "landscape");
                //parameters.setRotation(0);
                camera.setDisplayOrientation(0);
            }
            camera.setParameters(parameters);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2){
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        LogUtils.d("surfaceDestroyed----");
        //surfaceview关闭时，关闭预览，释放资源
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onDestroy(){
        if(surfaceHolder != null){
            surfaceView = null;
            surfaceHolder = null;
        }
        if(executorService!=null){
            executorService.shutdown();
            executorService=null;
        }
        if(bitmap!=null){
            bitmap.recycle();
            bitmap=null;
        }
        super.onDestroy();
    }

    Camera.PictureCallback jpegpic = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera){
            LogUtils.e("picbytes.length:"+bytes.length);
            ImageView imageView = new ImageView(CameraActivity.this);
            float f = (float) (screenW * 1.00 / screenH);
            float f1 = (float) (screenH * 1.00 / screenW);
            int orientaition=photoL.getOrientation();
            LinearLayout.LayoutParams params = null;
            LogUtils.d("jpegpic--");
            LogUtils.d("getheigt:" + photoL.getHeight() + "  f:" + f + "  f1:" + f1);
            //横竖屏的配置
            if(orientaition == LinearLayout.HORIZONTAL){
                params = new LinearLayout.LayoutParams((int) (photoL.getHeight() * (f > f1 ? f : f1)), ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(15, 5, 15, 0);
            }else{
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (photoL.getWidth() * (f > f1 ? f1 : f)));
                params.setMargins(5, 15, 0, 15);
            }
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            LogUtils.i("压缩前bitmap:"+bitmap.getByteCount());
            //压缩图片
            bitmap=changeBitmap(bitmap,orientaition);
            LogUtils.i("压缩后bitmap:"+bitmap.getByteCount());
            imageView.setImageBitmap(bitmap);
            photoL.addView(imageView);
            lineview.setVisibility(View.VISIBLE);
            photobytes = bytes;


        }
    };

    //开启预览
    private void startTake(){
        try{
            if(camera != null){
                camera.stopPreview();
            }
            if(camera == null){
                camera = Camera.open(cameraPosition);
            }
            if(surfaceHolder != null && camera != null){
                camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画
                camera.startPreview();//开始预览
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }




    //缩放
    private Bitmap changeBitmap(Bitmap bp,int orientation){
        float m=0;
        if(orientation==LinearLayout.VERTICAL){
            m=photoL.getWidth()*1.0f/(screenW>screenH?screenW:screenH);
        }else {
            m=photoL.getHeight()*1.0f/(screenW>screenH?screenH:screenW);
        }

        LogUtils.d("压缩比例m："+m);
        Matrix matrix = new Matrix();
        matrix.setScale(m, m);
        bitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);
        return bitmap;
    }
    //manifest add configchanged:"orientation" then execute this method
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation== Configuration.ORIENTATION_PORTRAIT){
            if(camera!=null){
                //camera.getParameters().set("","");
                camera.setDisplayOrientation(90);
            }
            LogUtils.i("screenChange to  portrait");
        }else if(newConfig.orientation== Configuration.ORIENTATION_LANDSCAPE){
            LogUtils.i("screenChange to land");
            if(camera!=null){
                camera.setDisplayOrientation(0);
            }
        }
    }
}
