package com.example.david.anothermovieapp.model;

public class MovieListResponse {
    private Movie[] results;
    private int page;
    private int total_results;
    private Dates dates;
    private int total_pages;

    public MovieListResponse() {}

    public int getPage() {
        return page;
    }

    public Movie[] getResults() {
        return results;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }
}
