package com.starcity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.starcity.utils.SpUtils;

/**
 * Created by sanmu on 2016/7/25.
 */
public  abstract class BaseActivity extends FragmentActivity {

    public Context context;
    //protected SharedPreferences sp;
    //protected SpUtils sp = new SpUtils();

    public ProgressDialog progressDialog;
    protected NotificationManager notificationManager;

    /**
     * 子类数据初始化方
     */
    protected abstract void initData();

    /**
     * 子类初始化id
     */
    protected abstract void initID();

    /**
     * 子类控件初始化方
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        //sp = getSharedPreferences(SpUtils.SP_NAME, Context.MODE_PRIVATE);
        SpUtils.setSharedPerference(context);

        initView(savedInstanceState);
        initID();
        initData();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 保存用户用户名方法
     *
     * @param userName
     */
    public void setUserId(String userName) {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("userName", userName);
//        editor.commit();
        SpUtils.saveString(SpUtils.USERID, userName, false);
    }
    /**
     * 获得用户用户名
     * @return
     */
    public String getUserId() {

        //return sp.getString("userName", null);
        return  SpUtils.getString(SpUtils.USERID, null);
    }

}
