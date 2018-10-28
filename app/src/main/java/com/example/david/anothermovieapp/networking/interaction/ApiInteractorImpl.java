package com.example.david.anothermovieapp.networking.interaction;

import com.example.david.anothermovieapp.model.MovieListResponse;
import com.example.david.anothermovieapp.networking.ApiService;
import com.example.david.anothermovieapp.model.MovieListType;
import com.example.david.anothermovieapp.util.Constants;

import retrofit2.Callback;

public class ApiInteractorImpl implements ApiInteractor {

    private ApiService apiService;

    public ApiInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getMovies(MovieListType movieListType, int page, Callback<MovieListResponse> moviesCallback) {
        apiService.getMovies(movieListType.toUrlCompatibleString(), Constants.API_KEY, page).enqueue(moviesCallback);
    }

    @Override
    public void searchMovies(String query, int page, Callback<MovieListResponse> moviesCallback) {
        apiService.searchMovies(Constants.API_KEY, query, page).enqueue(moviesCallback);
    }
}
