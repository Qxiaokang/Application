package com.example.aozun.testapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.example.aozun.testapplication.activity.CrashActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title 未捕获异常处理类
 * @author robinchen
 *
 */
public class CrashHandler implements UncaughtExceptionHandler{
	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	//CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context context;
	// 用来存储设备信息和异常信息
	private Map<String,String> infos = new HashMap<String,String>();
	// 用于格式化信息，作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	
	// 保证只有一个CrashHandler实例
	private CrashHandler(){
		
	}
	
	/**  获取CrashHandler实例，单例模式  */
	public static CrashHandler getInstance(){
		return INSTANCE;
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	public void init(Context context){
		this.context = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && mDefaultHandler !=null){
			// 如果用户没有处理则让系统默认的处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		}else{ 
			/*try{
				Thread.sleep(3000);
			}catch(InterruptedException e){
				Log.e(TAG,"error",e);
			}
			// 退出程序
			MainApplication.getInstance().exit();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);*/
			Intent intent = new Intent(context,CrashActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			MainApplication.getInstance().exit();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}
	
	private boolean handleException(Throwable ex){
		if(ex==null){
			return false;
		}
		// 使用Toast来显示异常信息
		/*new Thread(){
			@Override
			public void run(){
				Looper.prepare();
				Toast.makeText(context, "很抱歉,程序出现未知异常,系统即将退出,请重新登录.", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();*/
		// 搜集设备参数信息
		collectDeviceInfo(context);
		// 保存日志文件
		saveCrashInfoToFile(ex);
		return true;
	}
	
	/**
	 * 收集设备参数信息
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx){
		try{
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if(pi != null){
				String versionName = pi.versionName==null?"null":pi.versionName;
				String versionCode = pi.versionCode+"";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		}catch(NameNotFoundException e){
			Log.e(TAG, "an error occured when collect package info");
		}
		Field[] fields = Build.class.getDeclaredFields();
		for(Field field:fields){
			try{
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName()+" : "+field.get(null));
			}catch(Exception e){
				Log.e(TAG, "an error occured when collect crash info",e);
			}
		}
	}
	
	/**
	 * 保存错误信息到文件中
	 * @param ex
	 * @return   返回文件名称,便于将文件传送到服务器 
	 */
	private String saveCrashInfoToFile(Throwable ex){
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : infos.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key+"="+value+"\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while(cause != null){
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try{
			long timestamp = System.currentTimeMillis();
			String time = formatter.format(new Date());
			String fileName = "crash-"+time +"-"+timestamp+".log";
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/crash/";
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path+fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		}catch(Exception e){
			Log.e(TAG,"an error occured while writing file...",e);
		}
		return null;
	}
	

}
