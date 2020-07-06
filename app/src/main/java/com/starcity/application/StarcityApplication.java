//http://code1.okbase.net/codefile/FtpServerApp.java_2014010425502_6.htm
//Norman:参考了SwiFTP的源代码
package com.starcity.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.starcity.entity.QbQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StarcityApplication extends Application {
	private static final String TAG = StarcityApplication.class.getSimpleName();
	public static Context context;
	@Override
	public void onCreate() {
		super.onCreate();
		context=this;
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		//ftp
	}

}


