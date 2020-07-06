package com.starcity.config;

import com.starcity.api.ApiStores;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHaveTokenBuilder<T> implements IBuilder{

    @SuppressWarnings("unchecked")
    @Override
    public T createRetrofitService() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ApiStores.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(ApiStores.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(ApiStores.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //加入token
        OkHttpClient okClient = builder.addInterceptor(new RetrofitTokenInterceptor()).build();

        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl(ApiStores.API_SERVER_URL)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build();
        ApiStores apiStores = retrofit.create(ApiStores.class);
        return (T) apiStores;
    }
}
