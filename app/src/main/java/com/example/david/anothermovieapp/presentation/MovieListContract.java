package com.example.david.anothermovieapp.presentation;

import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.model.MovieListType;

public interface MovieListContract {
    interface Presenter {
        void requestFirstPageOfMovies();
        void requestMorePagesOfMovies();
    }

    interface View {
        void setMovieList(Movie[] movies);
        void addMovies(Movie[] movies);
    }
}
