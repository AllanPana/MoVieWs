package com.example.allan.moviews.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.allan.moviews.R;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.movieDetail.MovieDetailActivity;
import com.example.allan.moviews.util.MoviePrefsHelper;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */
public class MainActivity extends BaseActivity implements MainView,
        MovieAdapter.MovieOnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    //private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String TOP_RATED = "movie/top_rated";
    private static final String MOST_POPULAR = "movie/popular";
    //private static final String UPCOMING = "movie/upcoming";
    private MainPresenter mainPresenter;
    private MoviePrefsHelper moviePrefsHelper;
    private SharedPreferences sharedPreferences;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MoviePrefsHelper.MOVIE_PREFS, MODE_PRIVATE);
        moviePrefsHelper = new MoviePrefsHelper(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {
        mainPresenter = new MainPresenter(new MovieService(), moviePrefsHelper);
        mainPresenter.attachView(this);
        mainPresenter.setMovieData(moviePrefsHelper.getSortMovie());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.action_movie_popular) {
            mainPresenter.setMovieUrl(MOST_POPULAR);
        } else if (itemID == R.id.action_movie_top_rated) {
            mainPresenter.setMovieUrl(TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        mainPresenter.detachView();
    }

    @Override
    public void showListOfMovies(List<MovieItem> results) {
        MovieAdapter movieAdapter = new MovieAdapter(results, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(movieAdapter);
        mainPresenter.setToolBar(moviePrefsHelper.getSortMovie());

    }

    @Override
    public void displayToolBar(String toolBarTitle) {
        if (toolBarTitle != null) {
            toolbar.setTitle(toolBarTitle);
        }
        setSupportActionBar(toolbar);


    }

    @Override
    public void onMovieItemClick(int position) {
        startActivity(MovieDetailActivity
                .getIntent(MainActivity.this, mainPresenter.getSelectedMovie(position)));


    }

    //Listerner for the SharedPreference when preference value changed
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mainPresenter.setMovieData(moviePrefsHelper.getSortMovie());
    }
}
