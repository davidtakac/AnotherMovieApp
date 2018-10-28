package com.example.david.anothermovieapp.presentation;

import android.util.Log;

import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.networking.interaction.ApiInteractor;
import com.example.david.anothermovieapp.model.MovieListResponse;
import com.example.david.anothermovieapp.model.MovieListType;
import com.example.david.anothermovieapp.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter implements MovieListContract.Presenter {

    private ApiInteractor apiInteractor;

    private MovieListContract.View moviesView;

    private int loadedPages;

    private MovieListType listType;

    public MovieListPresenter(MovieListContract.View moviesView, MovieListType type, ApiInteractor apiInteractor) {
        this.moviesView = moviesView;
        this.apiInteractor = apiInteractor;
        this.listType = type;
        this.loadedPages = 0;
    }

    private Callback<MovieListResponse> createMoviesCallback() {
        return new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(Constants.LOG_TAG, "Successful movies list response.");

                    int page = response.body().getPage();
                    Movie[] movies = response.body().getResults();

                    if(page == 1){
                        moviesView.setMovieList(movies);
                    } else {
                        moviesView.addMovies(movies);
                    }
                    loadedPages = page;
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Log.d(Constants.LOG_TAG, "Unsuccessful movie list response");
                t.printStackTrace();
            }
        };
    }

    @Override
    public void requestFirstPageOfMovies() {
        apiInteractor.getMovies(listType, 1, createMoviesCallback());
    }

    @Override
    public void requestMorePagesOfMovies() {
        apiInteractor.getMovies(listType, loadedPages + 1, createMoviesCallback());
    }
}
