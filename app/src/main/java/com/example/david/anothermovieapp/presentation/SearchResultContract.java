package com.example.david.anothermovieapp.presentation;

import com.example.david.anothermovieapp.model.Movie;

public interface SearchResultContract {
    interface Presenter{
        void searchMovies(String query);
        void searchMoreMovies();

        void refreshMovieList();
    }

    interface View{
        void setSearchResults(Movie[] movies);
        void addSearchResults(Movie[] movies);
    }
}
