package com.starcity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starcity.BaseActivity;
import com.starcity.R;
import com.starcity.ThreadCallback;
import com.starcity.api.ApiStores;
import com.starcity.api.LoginOutMethod;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.entity.RResult;
import com.starcity.fragment.PrepareTestFragment;
import com.starcity.fragment.MyInfoFragment;
import com.starcity.fragment.TestedFragment;

import retrofit2.Call;

/**
 * Created by Administrator on 2016/8/30 0030.
 * 主要界面
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fm;
    private FragmentTransaction ft;

    private LinearLayout myInfoL;
    private LinearLayout prepareL;
    private LinearLayout testedL;

    private ImageView meImage;
    private ImageView prepareImage;
    private ImageView testedImage;

    private PrepareTestFragment prepareTestFragment;//待考试fragment
    private MyInfoFragment myInfoFragment;//个人信息fragment
    private TestedFragment testedFragment;//考试历史fragment
    private LoginOutMethod loginOutMethod;

    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();
    @Override
    protected void initData() {
        loginOutMethod=new LoginOutMethod(this,true);
        ft = fm.beginTransaction();
        ft.replace(R.id.Layout_main, prepareTestFragment);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initID() {

        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        meImage = (ImageView) findViewById(R.id.me);

        prepareImage = (ImageView) findViewById(R.id.prepare);

        testedImage = (ImageView) findViewById(R.id.naviga);

        myInfoL = (LinearLayout) findViewById(R.id.meL);
        prepareL = (LinearLayout) findViewById(R.id.prepareL);
        testedL = (LinearLayout) findViewById(R.id.testedL);
        myInfoL.setOnClickListener(this);
        prepareL.setOnClickListener(this);
        testedL.setOnClickListener(this);

        prepareTestFragment = new PrepareTestFragment();
        myInfoFragment = new MyInfoFragment();
        testedFragment = new TestedFragment();
        fm = getSupportFragmentManager();
    }
    //  android
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meL:
                meImage.setBackgroundResource(R.drawable.me_selected);
                testedImage.setBackgroundResource(R.drawable.history);
                prepareImage.setBackgroundResource(R.drawable.prepare);
                ft = fm.beginTransaction();
                ft.replace(R.id.Layout_main, myInfoFragment);
                ft.commit();
                break;
            case R.id.prepareL:
                meImage.setBackgroundResource(R.drawable.me);
                testedImage.setBackgroundResource(R.drawable.history);
                prepareImage.setBackgroundResource(R.drawable.prepare_selected);

                ft = fm.beginTransaction();
                ft.replace(R.id.Layout_main, prepareTestFragment);
                ft.commit();
                break;
            case R.id.testedL:
                meImage.setBackgroundResource(R.drawable.me);
                testedImage.setBackgroundResource(R.drawable.history_selected);
                prepareImage.setBackgroundResource(R.drawable.prepare);
                ft = fm.beginTransaction();
                ft.replace(R.id.Layout_main, testedFragment);
                ft.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        Call<RResult> logout = retrofitService.logout();
                        logout.enqueue(loginOutMethod);
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }



}
