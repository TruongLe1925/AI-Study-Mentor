package com.bonanhtai.aistudymentor.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(Context context) {
        initializeRetrofit(context);
    }

    public void initializeRetrofit(Context context) {
        // Create Interceptor to add JWT Token to headers
        Interceptor authInterceptor = chain -> {
            Request original = chain.request();

            // Access SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("jwt_token", null);

            Request.Builder requestBuilder = original.newBuilder()
                    .method(original.method(), original.body());

            // Add Authorization header if token exists
            if (token != null) {
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor) // Add the interceptor here
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.73.178:8080/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
