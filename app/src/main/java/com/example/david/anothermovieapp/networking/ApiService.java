package com.example.david.anothermovieapp.networking;

import com.example.david.anothermovieapp.model.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/{movie_list_type}")
    Call<MovieListResponse> getMovies(@Path("movie_list_type") String movieListType, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("search/movie")
    Call<MovieListResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);
}
