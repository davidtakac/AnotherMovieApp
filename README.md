# AnotherMovieApp
An app that lists movies from the TMDB API. Displays some default categories such as top rated, upcoming etc. and enables searching. 
Overview: MVP architecture, Retrofit, Picasso, Fragments, ViewPager, RecyclerView, Material Design

# Main screen
Uses ViewPager with Fragments that contain RecyclerView's which display top rated, upcoming and other categories of movies. Each fragment has lazy loading and swipe to refresh. 

# Search screen
Uses RecyclerView that displays movie results. Also has lazy loading and swipe to refresh. 
