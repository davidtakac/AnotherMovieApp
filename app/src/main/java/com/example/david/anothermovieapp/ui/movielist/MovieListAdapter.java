package com.example.david.anothermovieapp.ui.movielist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.anothermovieapp.R;
import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public void setMovies(Movie[] movies) {
        Log.d(Constants.LOG_TAG, "setting movies...");
        this.movies = new ArrayList<>();
        Collections.addAll(this.movies, movies);

        notifyDataSetChanged();
    }

    public void addMovies(Movie[] movies){
        Log.d(Constants.LOG_TAG, "adding movies...");
        int positionStart = this.movies.size();
        Collections.addAll(this.movies, movies);

        notifyItemRangeInserted(positionStart, this.movies.size());
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View movieView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_movie, viewGroup, false);

        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int pos) {
        if(movies == null){
            throw new IllegalStateException("Movie dataset not initialized! Use setMovies().");
        }

        Movie movie = movies.get(pos);

        movieViewHolder.tvTitle.setText(movie.getTitle());
        movieViewHolder.tvDescription.setText(movie.getOverview());
        Picasso.get().load(Constants.BASE_IMG_TMDB_API_URL + Constants.MOVIE_POSTER_THUMBNAIL_SIZE + movie.getPosterPath()).into(movieViewHolder);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements Target {
        @BindView(R.id.imageview_listitem_movietitle)
        TextView tvTitle;

        @BindView(R.id.imageview_listitem_moviecover)
        ImageView ivCover;

        @BindView(R.id.textview_listitem_moviedesc)
        TextView tvDescription;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivCover.setImageDrawable(new BitmapDrawable(bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            ivCover.setImageDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            ivCover.setImageDrawable(placeHolderDrawable);
        }
    }
}
