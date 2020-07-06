package com.starcity.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.starcity.activity.LoginActivity;
import com.starcity.entity.RResult;
import com.starcity.utils.SpUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginOutMethod implements Callback<RResult> {

    private Activity activity;
    private boolean exitApp;

    public LoginOutMethod(Activity activity, boolean exitApp) {
        this.activity = activity;
        this.exitApp = exitApp;
    }

    private void Return2Login() {
        clearCash();
        Intent intent = new Intent(activity, LoginActivity.class);

        activity.startActivity(intent);
        activity.finish();
    }

    private void clearCash() {//清理缓存
        SpUtils.saveString(SpUtils.USERNAME, "", false);
        SpUtils.saveString(SpUtils.ACTUAL_NAME, "", false);
        SpUtils.saveString(SpUtils.BIRTHDAY, "", false);
        SpUtils.saveString(SpUtils.USERID, "", false);
        SpUtils.saveString(SpUtils.TOKEN, "", false);
        SpUtils.saveString(SpUtils.EMPLOYER, "", false);
        SpUtils.saveString(SpUtils.PHONE, "", false);
    }

    @Override
    public void onResponse(Call<RResult> call, Response<RResult> response) {
        String strDialogMsg = "退出成功";
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("提示")
                .setMessage(strDialogMsg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (exitApp) {
                            System.exit(0);
                        } else {
                            Return2Login();
                        }
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onFailure(Call<RResult> call, Throwable t) {
        String strDialogMsg = "退出登录失败: 请检查网络连接是否正常 ";
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("提示")
                .setMessage(strDialogMsg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Return2Login();
                    }
                }).create();
        dialog.show();
    }
}
