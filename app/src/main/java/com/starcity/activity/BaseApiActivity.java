package com.starcity.activity;

import com.starcity.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Bob
 * @date 2018/5/3
 */

public abstract class BaseApiActivity<T> extends BaseActivity implements Callback<T> {

    public abstract void onCallApiSuccess(Call<T> call, Response<T> response);

    public abstract void onCallApiFailure(Call<T> call, Throwable throwable);

    @Override
    public void onResponse(final Call<T> call, final Response<T> response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onCallApiSuccess(call, response);
            }
        });
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable throwable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onCallApiFailure(call, throwable);
            }
        });
    }
}
