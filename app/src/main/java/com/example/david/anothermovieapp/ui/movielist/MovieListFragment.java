package com.example.david.anothermovieapp.ui.movielist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david.anothermovieapp.App;
import com.example.david.anothermovieapp.R;
import com.example.david.anothermovieapp.model.Movie;
import com.example.david.anothermovieapp.model.MovieListType;
import com.example.david.anothermovieapp.presentation.MovieListContract;
import com.example.david.anothermovieapp.presentation.MovieListPresenter;
import com.example.david.anothermovieapp.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment implements MovieListContract.View {

    // TODO: 10/26/2018 cache results in database and load from there if no internet

    @BindView(R.id.recyclerview_movielistfragment_movielist)
    RecyclerView rvMovies;

    @BindView(R.id.swiperefresh_movielistfragment)
    SwipeRefreshLayout srMovies;

    MovieListAdapter moviesAdapter;

    MovieListContract.Presenter presenter;

    public static MovieListFragment newInstance(MovieListType type) {
        //as per Google docs, Fragments should only have a single constructor with no arguments.
        //arguments need to be passed in via Bundles and can be retrived by getArguments();

        Bundle data = new Bundle();
        data.putSerializable(Constants.LISTTYPE_BUNDLE_KEY, type);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(data);

        return movieListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movielist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        moviesAdapter = new MovieListAdapter();
        srMovies.setOnRefreshListener(createOnRefreshListener());
        presenter = new MovieListPresenter(this,
                (MovieListType) getArguments().getSerializable(Constants.LISTTYPE_BUNDLE_KEY),
                App.getApiInteractor());
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        requestFirstPageOfMovies();
    }

    @Override
    public void setMovieList(Movie[] movies) {
        setLoading(false);
        moviesAdapter.setMovies(movies);
    }

    @Override
    public void addMovies(Movie[] movies) {
        setLoading(false);
        moviesAdapter.addMovies(movies);
    }

    private void initRecyclerView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(moviesAdapter);
        rvMovies.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //detects when the recyclerview is scrolled all the way to the end.
                if (!recyclerView.canScrollVertically(1)) {
                    requestMoreMovies();
                }
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener createOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestFirstPageOfMovies();
            }
        };
    }

    private void requestFirstPageOfMovies() {
        setLoading(true);
        presenter.requestFirstPageOfMovies();
    }

    private void requestMoreMovies() {
        setLoading(true);
        presenter.requestMorePagesOfMovies();
    }

    private void setLoading(boolean isLoading){
        srMovies.setRefreshing(isLoading);
    }
}
