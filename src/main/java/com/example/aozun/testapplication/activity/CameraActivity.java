package com.example.aozun.testapplication.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.utils.LogUtils;
import com.example.aozun.testapplication.utils.UniversalUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by HHD-H-I-0369 on 2017/2/28.
 * 相机页面
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener,SurfaceHolder.Callback{
    private Button takephoto,cancleC,changeC;
    private String picname;
    private static final int TAKE_PHOTO_CODE = 3;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "images" + File.separator + "takePhotos";
    private SurfaceView surfaceView;
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private int cameraPosition=1;//0代表前置，1代表后置
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
        //设置手机屏幕朝向，一共有7种
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //SCREEN_ORIENTATION_BEHIND： 继承Activity堆栈中当前Activity下面的那个Activity的方向
        //SCREEN_ORIENTATION_LANDSCAPE： 横屏(风景照) ，显示时宽度大于高度
        //SCREEN_ORIENTATION_PORTRAIT： 竖屏 (肖像照) ， 显示时高度大于宽度
        //SCREEN_ORIENTATION_SENSOR  由重力感应器来决定屏幕的朝向,它取决于用户如何持有设备,当设备被旋转时方向会随之在横屏与竖屏之间变化
        //SCREEN_ORIENTATION_NOSENSOR： 忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
        //SCREEN_ORIENTATION_UNSPECIFIED： 未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择
        //SCREEN_ORIENTATION_USER： 用户当前的首选方向

        setContentView(R.layout.activity_camera);
        initViews();

    }

    private void initViews(){
        File file=new File(path);
        if(file.exists()){
            UniversalUtils.delFile(file);
        }else {
            file.mkdirs();
        }
        takephoto = (Button) findViewById(R.id.take);
        cancleC= (Button) findViewById(R.id.bt_cancel);
        changeC= (Button) findViewById(R.id.change_id);
        takephoto.setOnClickListener(this);
        cancleC.setOnClickListener(this);
        changeC.setOnClickListener(this);
        surfaceView= (SurfaceView) findViewById(R.id.surface_id);
        surfaceHolder=surfaceView.getHolder();//获得句柄
        surfaceHolder.addCallback(this);//添加回调
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//不维护自己的缓冲区，等待屏幕渲染引擎将内容摄推送到用户面前
    }

    @Override
    public void onClick(View view){
       switch(view.getId()){
           case R.id.take:


               break;
           case R.id.cancel:
                CameraActivity.this.finish();
               break;

           case R.id.change_id:
               int cameracout=0;
               Camera.CameraInfo cameraInfo=new Camera.CameraInfo();
               cameracout=Camera.getNumberOfCameras();//得到摄相头的个数
               if(cameracout!=0){
                   for(int i = 0; i < cameracout; i++){
                       Camera.getCameraInfo(i,cameraInfo);//得到每一个摄相头的信息
                       if(cameraPosition==1){
                           //切换为前置
                           if(cameraInfo.facing==Camera.CameraInfo.CAMERA_FACING_FRONT){
                               camera.stopPreview();//停掉原来的摄相头
                               camera.release();//释放资源
                               camera=null;//取消摄相头
                               camera=Camera.open(i);//打开当前选中的摄相头
                               try{
                                   camera.setPreviewDisplay(surfaceHolder);//显示取景
                                   camera.startPreview();//开始预览
                                   cameraPosition=0;
                               }catch(IOException e){
                                   LogUtils.d("摄相头切换失败");
                                   e.printStackTrace();
                               }
                               break;
                           }
                       }else {
                            //前置变为后置
                           if(cameraInfo.facing==Camera.CameraInfo.CAMERA_FACING_BACK){
                               camera.stopPreview();//停掉原来的摄相头
                               camera.release();//释放资源
                               camera=null;//取消摄相头
                               camera=Camera.open(i);//打开当前选中的摄相头
                               try{
                                   camera.setPreviewDisplay(surfaceHolder);//显示取景
                                   camera.startPreview();//开始预览
                                   cameraPosition=1;
                               }catch(IOException e){
                                   LogUtils.d("摄相头切换失败");
                                   e.printStackTrace();
                               }
                               break;

                           }
                       }
                   }
               }else {
                   UniversalUtils.getInstance().showToast(this,"相机启动失败");
               }
               break;
       }
    }

    private void takePhotos(){
        camera.startPreview();
        picname = UniversalUtils.getInstance().getUUID()+".jpg";
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
        if(resultCode==0){
            return;
        }

    }




    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        //当sufaceview创建时开启相机
        if(camera==null){
            camera=Camera.open();
            try{
                camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览


            }catch(IOException e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2){


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
    //surfaceview关闭时，关闭预览，释放资源
        camera.stopPreview();
        camera.release();
        camera=null;
        surfaceHolder=null;
        surfaceView=null;
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }


    Camera.AutoFocusCallback autoFocus=new Camera.AutoFocusCallback(){
        @Override
        public void onAutoFocus(boolean b, Camera camera){


        }
    };
}
