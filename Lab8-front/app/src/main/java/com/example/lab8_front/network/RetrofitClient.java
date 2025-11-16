package com.example.lab8_front.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static String SESSION_ID = null;  // храним JSESSIONID между запросами

    public static void setSessionId(String id) {
        SESSION_ID = id;
    }

    public static Retrofit getClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (SESSION_ID != null) {
            clientBuilder.addInterceptor(new SessionInterceptor(SESSION_ID));
        }
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Lab6/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();
    }
}
