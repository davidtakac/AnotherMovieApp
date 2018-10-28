package com.example.david.anothermovieapp.ui.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.david.anothermovieapp.App;
import com.example.david.anothermovieapp.R;
import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.presentation.SearchResultContract;
import com.example.david.anothermovieapp.presentation.SearchResultPresenter;
import com.example.david.anothermovieapp.ui.movielist.MovieListAdapter;
import com.example.david.anothermovieapp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity implements SearchResultContract.View {
    // TODO: 10/26/2018 add searching by keywords(research tmdb api)

    @BindView(R.id.recyclerview_searchresult_searchresults)
    RecyclerView rvSearchResults;

    @BindView(R.id.swiperefresh_searchresult)
    SwipeRefreshLayout srSearchResults;

    MovieListAdapter movieAdapter;

    SearchResultContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        ButterKnife.bind(this);

        movieAdapter = new MovieListAdapter();
        presenter = new SearchResultPresenter(this, App.getApiInteractor());
        srSearchResults.setOnRefreshListener(createOnRefreshListener());
        initRecyclerView();

        searchMovies(getIntent().getStringExtra(Constants.SEARCH_INTENT_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        initSearchView(menu.findItem(R.id.menuitem_search));
        return true;
    }

    private void initSearchView(MenuItem searchItem) {
        //expands searchview menu item so we can set the query to be the one from the pager activity
        //(adds consistency between activities)
        searchItem.expandActionView();

        SearchView searchView = (SearchView) searchItem.getActionView();
        //sets searchview query to be the search query the user entered from the pager activity
        searchView.setQuery(getIntent().getStringExtra(Constants.SEARCH_INTENT_KEY), false);
        searchView.setQueryHint(getResources().getString(R.string.menu_searchhint));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMovies(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initRecyclerView() {
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(movieAdapter);
        rvSearchResults.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvSearchResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    searchMoreMovies();
                }
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener createOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMovieList();
            }
        };
    }

    private void searchMovies(String query){
        setLoading(true);
        presenter.searchMovies(query);
    }

    private void searchMoreMovies() {
        setLoading(true);
        presenter.searchMoreMovies();
    }

    private void refreshMovieList() {
        setLoading(true);
        presenter.refreshMovieList();
    }

    @Override
    public void setSearchResults(Movie[] movies) {
        setLoading(false);
        movieAdapter.setMovies(movies);
    }

    @Override
    public void addSearchResults(Movie[] movies) {
        setLoading(false);
        movieAdapter.addMovies(movies);
    }

    private void setLoading(boolean isLoading){
        srSearchResults.setRefreshing(isLoading);
    }
}
