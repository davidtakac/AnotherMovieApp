package com.example.david.anothermovieapp.ui.movielistpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.david.anothermovieapp.ui.movielist.MovieListFragment;
import com.example.david.anothermovieapp.model.MovieListType;

public class MovieListPagerAdapter extends FragmentPagerAdapter {

    public MovieListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MovieListFragment.newInstance(MovieListType.values()[position]);
    }

    @Override
    public int getCount() {
        return MovieListType.values().length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return MovieListType.values()[position].toStylisedString();
    }
}
