package com.starcity.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starcity.api.ApiStores;
import com.starcity.api.LoginOutMethod;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.entity.RResult;
import com.starcity.R;
import com.starcity.activity.LoginActivity;
import com.starcity.utils.SpUtils;
import com.starcity.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LiuShao on 2016/9/1.
 */
public class MyInfoFragment extends BaseFragment implements View.OnClickListener {//个人信息fragment

    private Button tv_exit;
    private TextView tv_username;
    private TextView tv_phone;
    private TextView tv_actual_name;
    private TextView tv_birthday;
    private TextView tv_employer;
    private LoginOutMethod loginOutMethod;

    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();

    @Override
    protected void initData() {
        loginOutMethod=new LoginOutMethod(getActivity(),false);
        String username = SpUtils.getString(SpUtils.USERNAME, "");
        String actualName = SpUtils.getString(SpUtils.ACTUAL_NAME, "");
        String birthday = SpUtils.getString(SpUtils.BIRTHDAY, "");
        String employer = SpUtils.getString(SpUtils.EMPLOYER, "");
        String phone = SpUtils.getString(SpUtils.PHONE, "");
        tv_username.setText(username);
        tv_phone.setText(phone);
        tv_actual_name.setText(actualName);
        tv_birthday.setText(birthday);
        tv_employer.setText(employer);
    }

    @Override
    protected void initID() {
        tv_username= (TextView) view.findViewById(R.id.tv_username);
        tv_phone= (TextView) view.findViewById(R.id.tv_phone);
        tv_actual_name= (TextView) view.findViewById(R.id.tv_actual_name);
        tv_birthday= (TextView) view.findViewById(R.id.tv_birthday);
        tv_employer= (TextView) view.findViewById(R.id.tv_employer);
        tv_exit = (Button) view.findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(this);
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_myinfo;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("提示")
                        .setMessage("确定退出登录吗")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Call<RResult> logout = retrofitService.logout();
                                logout.enqueue(loginOutMethod);
                            }
                        }).create();
                dialog.show();
                break;
        }
    }



}
