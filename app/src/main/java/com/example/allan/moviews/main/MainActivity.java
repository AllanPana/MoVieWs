package com.example.allan.moviews.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.allan.moviews.R;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.util.MoviePrefsHelper;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */
public class MainActivity extends BaseActivity implements MainView,
        MovieAdapter.MovieOnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TOP_RATED = "movie/top_rated";
    private static final String MOST_POPULAR = "movie/popular";
    private static final String UPCOMING = "movie/upcoming";
    private MainPresenter mainPresenter;
    private MovieAdapter movieAdapter;
    private MoviePrefsHelper moviePrefsHelper;
    private MenuItem popularMovieItem;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {

        mainPresenter.setMovieData();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {
        SharedPreferences sharedPreferences = getSharedPreferences(MoviePrefsHelper.MOVIE_PREFS, MODE_PRIVATE);
        moviePrefsHelper = new MoviePrefsHelper(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mainPresenter = new MainPresenter(new MovieService(), moviePrefsHelper);
        mainPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        popularMovieItem = menu.findItem(R.id.action_movie_popular);
        if (moviePrefsHelper.getSortMovie().equals(MOST_POPULAR)){
            popularMovieItem.setChecked(true);
        }
        Log.e("menu", popularMovieItem.getTitle().toString());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        String movieUrl = "";
        if (itemID == R.id.action_movie_popular) {
            movieUrl = MOST_POPULAR;
        } else if (itemID == R.id.action_movie_top_rated) {
            movieUrl = TOP_RATED;
        }

        item.setChecked(true);
        mainPresenter.setMovieUrl(movieUrl);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListOfMovies(List<MovieItem> results) {
        movieAdapter = new MovieAdapter(results, this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onMovieItemClick(int position) {
        //todo create an intent for details activity

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mainPresenter.setMovieData();
        //movieAdapter.notifyDataSetChanged();

    }
}
