package com.example.lab8_front.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SessionInterceptor implements Interceptor {
    private final String sessionId;

    public SessionInterceptor(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Cookie", "JSESSIONID=" + sessionId)
                .build();
        return chain.proceed(request);
    }
}
