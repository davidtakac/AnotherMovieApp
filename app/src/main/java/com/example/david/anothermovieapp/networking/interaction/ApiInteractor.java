package com.example.david.anothermovieapp.networking.interaction;

import com.example.david.anothermovieapp.model.MovieListResponse;
import com.example.david.anothermovieapp.model.MovieListType;

import retrofit2.Callback;

public interface ApiInteractor {
    void getMovies(MovieListType movieListType, int page, Callback<MovieListResponse> moviesCallback);
    void searchMovies(String query, int page, Callback<MovieListResponse> moviesCallback);
}
