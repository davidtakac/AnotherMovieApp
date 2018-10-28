package com.example.david.anothermovieapp.model;

public enum MovieListType {
    UPCOMING, TOP_RATED, POPULAR;

    public String toUrlCompatibleString(){
        return this.toString().toLowerCase();
    }

    public String toStylisedString(){
        return this.toString().replace('_', ' ');
    }
}
