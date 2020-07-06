package com.starcity.config;

/**
 * Retrofit and RetrofitService Factory
 *
 * @author Bob
 * @date 2017/12/28
 */

public class RetrofitHaveTokenFactory implements ICreate {

    @Override
    public IBuilder getRetrofit() {
        return new RetrofitHaveTokenBuilder();
    }
}
