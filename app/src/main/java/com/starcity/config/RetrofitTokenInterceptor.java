package com.starcity.config;

import com.starcity.utils.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * add cookie interceptor
 *
 * @author Bob
 * @date 2017/12/28
 */

class RetrofitTokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", SpUtils.getString(SpUtils.TOKEN, null))
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
