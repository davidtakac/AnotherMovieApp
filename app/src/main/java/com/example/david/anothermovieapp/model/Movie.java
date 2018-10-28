package com.example.david.anothermovieapp.model;

public class Movie {
    private int vote_count;
    private int id;
    private boolean video;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private int[] genre_ids;
    private String backdrop_path;
    private boolean adult;
    private String overview;
    private String release_date;

    public Movie() {}

    public String getPosterPath() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }
}
