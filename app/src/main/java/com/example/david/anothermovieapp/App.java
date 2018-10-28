package com.example.david.anothermovieapp;

import android.app.Application;

import com.example.david.anothermovieapp.networking.interaction.ApiInteractor;
import com.example.david.anothermovieapp.networking.interaction.ApiInteractorImpl;
import com.example.david.anothermovieapp.networking.ApiService;
import com.example.david.anothermovieapp.networking.RetrofitUtil;

import retrofit2.Retrofit;

public class App extends Application {

    private static ApiInteractor apiInteractor;

    @Override
    public void onCreate() {
        super.onCreate();

        final Retrofit retrofit = RetrofitUtil.getRetrofit();
        final ApiService apiService = retrofit.create(ApiService.class);

        apiInteractor = new ApiInteractorImpl(apiService);
    }

    public static ApiInteractor getApiInteractor(){
        return apiInteractor;
    }
}
