package com.example.david.anothermovieapp.ui.movielistpager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.david.anothermovieapp.R;
import com.example.david.anothermovieapp.ui.searchresult.SearchResultActivity;
import com.example.david.anothermovieapp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListPagerActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_moviepager_movielist)
    ViewPager vpMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviepager);
        ButterKnife.bind(this);

        vpMovieList.setAdapter(new MovieListPagerAdapter(getSupportFragmentManager()));
        initTabLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        initSearchView(menu.findItem(R.id.menuitem_search));
        return true;
    }

    @Override
    public void onBackPressed() {
        if(vpMovieList.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            vpMovieList.setCurrentItem(vpMovieList.getCurrentItem() - 1);
        }
    }

    private void initSearchView(final MenuItem searchMenuItem) {
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.menu_searchhint));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(Constants.LOG_TAG, "query string: " + s);
                Intent searchResultIntent = new Intent(MovieListPagerActivity.this, SearchResultActivity.class);
                searchResultIntent.putExtra(Constants.SEARCH_INTENT_KEY, s);
                searchMenuItem.collapseActionView();
                startActivity(searchResultIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initTabLayout() {
        TabLayout tl = findViewById(R.id.tablayout_moviepager);
        tl.setupWithViewPager(vpMovieList);
    }
}
