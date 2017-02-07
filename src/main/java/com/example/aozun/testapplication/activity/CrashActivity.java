package com.example.aozun.testapplication.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import com.example.aozun.testapplication.R;
import com.example.aozun.testapplication.service.LockService;
import com.example.aozun.testapplication.utils.LogUtils;

/**
 * @title 异常崩溃提示界面
 * @author robinchen
 *
 */
public class CrashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crash);
	    
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("很抱歉,程序出现未知异常,系统即将退出,请重新登录.");

		builder.setTitle("系统提示");

		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				LogUtils.e("Crash----");
				applicationShared.edit().putInt("pwdtimes",0).commit();
				//将锁屏服务关闭
				Intent it = new Intent(CrashActivity.this,LockService.class);
				CrashActivity.this.stopService(it);
				CrashActivity.this.finish();

			}
		});

		builder.create().show();

	}

}
