package com.net.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/9.
 */

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("User-agent", "Mozilla/4.0")
                .build();
        return chain.proceed(request);
    }
}
