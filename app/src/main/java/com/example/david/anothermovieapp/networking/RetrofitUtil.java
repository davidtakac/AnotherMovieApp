package com.example.david.anothermovieapp.networking;

import com.example.david.anothermovieapp.util.Constants;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_TMDB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    private static Interceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
