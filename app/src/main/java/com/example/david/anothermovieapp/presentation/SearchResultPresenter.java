package com.example.david.anothermovieapp.presentation;

import android.util.Log;

import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.model.MovieListResponse;
import com.example.david.anothermovieapp.networking.interaction.ApiInteractor;
import com.example.david.anothermovieapp.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultPresenter implements SearchResultContract.Presenter {

    private SearchResultContract.View searchResultView;

    private ApiInteractor apiInteractor;

    private String lastQuery;

    private int loadedPages;

    public SearchResultPresenter(SearchResultContract.View searchResultView, ApiInteractor apiInteractor) {
        this.apiInteractor = apiInteractor;
        this.searchResultView = searchResultView;
        this.loadedPages = 0;
    }

    @Override
    public void searchMovies(String query) {
        lastQuery = query;
        apiInteractor.searchMovies(query, 1, createSearchResultsCallback());
    }

    @Override
    public void searchMoreMovies() {
        apiInteractor.searchMovies(lastQuery, loadedPages + 1, createSearchResultsCallback());
    }

    @Override
    public void refreshMovieList() {
        searchMovies(lastQuery);
    }

    private Callback<MovieListResponse> createSearchResultsCallback() {
        return new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(Constants.LOG_TAG, "Received successful search results response.");

                    int page = response.body().getPage();
                    Movie[] movies = response.body().getResults();

                    if(page == 1){
                        searchResultView.setSearchResults(movies);
                    } else {
                        searchResultView.addSearchResults(movies);
                    }
                    loadedPages = page;
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                Log.d(Constants.LOG_TAG, "Unsuccessful search results response.");
                t.printStackTrace();
            }
        };
    }
}
