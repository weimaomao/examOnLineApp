package com.starcity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starcity.BaseActivity;
import com.starcity.R;
import com.starcity.api.ApiStores;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitNoTokenFactory;
import com.starcity.constants.ConstantStore;
import com.starcity.entity.ImageCode;
import com.starcity.entity.LoginUserDTO;
import com.starcity.entity.LoginUserForm;
import com.starcity.entity.RResult;
import com.starcity.entity.UserInfo;
import com.starcity.utils.Base64Util;
import com.starcity.utils.RSAUtil;
import com.starcity.utils.SpUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sanmu on 2016/7/25.
 */
public class LoginActivity extends BaseActivity implements Callback<RResult<LoginUserDTO>> {
    private TextView tv_login;
    private EditText et_username;
    private EditText et_password;
    private EditText et_code;
    private ImageView iv_code;
    boolean islogining = false;
    //图片验证码的uuid
    private String uuid = null;
    private String username = null;
    private String password = null;
    private String code = null;

    public final static int MSG_LOGIN = 0;
    Call<RResult<ImageCode>> mImageCodeCall;
    Call<RResult<LoginUserDTO>> mLoginCall;

    ICreate iCreate = new RetrofitNoTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();

    public LoginActivity() {
        //获取验证码
        getImageCode();
    }

    /**
     * 获取验证码
     */
    private void getImageCode() {
        mImageCodeCall = retrofitService.getImageCode();
        mImageCodeCall.enqueue(new Callback<RResult<ImageCode>>() {
            @Override
            public void onResponse(final Call<RResult<ImageCode>> call, final Response<RResult<ImageCode>> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RResult<ImageCode> responseBody = response.body();
                        if (mImageCodeCall != null && mImageCodeCall.request().equals(call.request())) {
                            if (responseBody == null || responseBody.getCode() != 200) {
                                Toast.makeText(LoginActivity.this, responseBody == null ? "服务异常" : responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ImageCode imageCode = responseBody.getResult();
                            uuid = imageCode.getUuid();
                            System.out.println(uuid);
                            String img = imageCode.getImg();
                            Bitmap bitmap = stringToBitmap(img);
                            //设置图片验证码
                            iv_code.setImageBitmap(bitmap);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RResult<ImageCode>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(LoginActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        iv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageCode();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (islogining) return;
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                code = et_code.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(LoginActivity.this, "请输入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                islogining = true;
                onLogin();
            }
        });
    }

    private void onLogin() {
        //重新获取验证码
        getImageCode();

        //加密密码
        /*RSA rsa = new RSA(ConstantStore.privateKey, ConstantStore.publisKey);
        String encryptPassword = null;
        encryptPassword = StrUtil.str(rsa.encrypt(password, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
        String decryptPassword  = StrUtil.str(rsa.decrypt(encryptPassword, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
*/
        //加密密码
        String encryptPassword=null;
        // 从字符串中得到公钥
        try {
            PublicKey publicKey = RSAUtil.loadPublicKey(ConstantStore.publisKey);
            // 加密
            byte[] encryptByte = RSAUtil.encryptData(password.getBytes(), publicKey);
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            encryptPassword = Base64Util.encode(encryptByte);
            System.out.println(encryptPassword);

            // 从字符串中得到私钥
             PrivateKey privateKey = RSAUtil.loadPrivateKey(ConstantStore.privateKey);
            // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
            byte[] decryptByte = RSAUtil.decryptData(Base64Util.decode(encryptPassword), privateKey);
            String decryptStr = new String(decryptByte);
            System.out.println(decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //登录请求
        LoginUserForm loginUser = new LoginUserForm();
        loginUser.setCode(code);
        loginUser.setUsername(username);
        loginUser.setPassword(encryptPassword);
        loginUser.setUuid(uuid);
        mLoginCall = retrofitService.login(loginUser);
        mLoginCall.enqueue(this);
    }

    /**
     * string转bitmap
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void initID() {
        tv_login = (TextView) findViewById(R.id.tv_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (ImageView) findViewById(R.id.iv_code);
    }

    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        LoginActivity.this.finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 登录的回调  进行页面跳转
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<RResult<LoginUserDTO>> call, Response<RResult<LoginUserDTO>> response) {
        islogining = false;
        RResult<LoginUserDTO> body = response.body();
        if (body == null || body.getCode() != 200) {
            Toast.makeText(LoginActivity.this, body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LoginUserDTO result = body.getResult();
        SpUtils.saveString(SpUtils.TOKEN,result.getToken(),false);
        UserInfo user = result.getUser();
        SpUtils.saveString(SpUtils.USERNAME,user.getUsername(),false);
        SpUtils.saveString(SpUtils.USERID,user.getId(),false);
        if(user.getEmployee()!=null){
            SpUtils.saveString(SpUtils.ACTUAL_NAME,user.getEmployee().getActualName(),false);
            SpUtils.saveString(SpUtils.BIRTHDAY, sdf.format(new Date(user.getEmployee().getBirthday())),false);
            SpUtils.saveString(SpUtils.EMPLOYER,user.getEmployee().getEmployer(),false);
            SpUtils.saveString(SpUtils.PHONE,user.getEmployee().getPhone(),false);
        }
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void onFailure(Call<RResult<LoginUserDTO>> call, Throwable t) {
        islogining = false;
        System.out.println(t.getMessage());
        Toast.makeText(LoginActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
    }
}
