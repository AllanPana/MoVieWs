package com.example.allan.moviews.main;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allan.moviews.R;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.favData.FavMovieContract;
import com.example.allan.moviews.movieDetail.MovieDetailActivity;
import com.example.allan.moviews.util.MoviePrefsHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */
public class MainActivity extends BaseActivity implements MainView,
        MovieAdapter.MovieOnItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    //private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String TOP_RATED = "movie/top_rated";
    private static final String MOST_POPULAR = "movie/popular";
    private static final int FAV_MOVIE_LOADER_ID = 0;
    public static final String FAVORITE = "favorite";
    //private static final String UPCOMING = "movie/upcoming";
    private MainPresenter mainPresenter;
    private MoviePrefsHelper moviePrefsHelper;
    private SharedPreferences sharedPreferences;
    private FavMovieLoader favMovieLoader;
    private MovieAdapter movieAdapter;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean isfav;
    private LoaderManager loaderManager;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MoviePrefsHelper.MOVIE_PREFS, MODE_PRIVATE);
        moviePrefsHelper = new MoviePrefsHelper(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        loaderManager  = getSupportLoaderManager();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {
        mainPresenter = new MainPresenter(new MovieService(), moviePrefsHelper);
        mainPresenter.attachView(this);
        String movieSort = moviePrefsHelper.getSortMovie();
        if (movieSort.equals(FAVORITE)){
            startLoader(loaderManager);
        }else {
            mainPresenter.loadMoviesFromServer(moviePrefsHelper.getSortMovie());
        }
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
            isfav = false;
            mainPresenter.setMovieUrl(MOST_POPULAR);
        } else if (itemID == R.id.action_movie_top_rated) {
            isfav = false;
            mainPresenter.setMovieUrl(TOP_RATED);
        }else if (itemID == R.id.action_movie_favorite) {
            isfav = true;
            mainPresenter.setMovieUrl(FAVORITE);
           startLoader(loaderManager);
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
        movieAdapter = new MovieAdapter(results, this, isfav);
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
        if (isfav){
           startLoader(loaderManager);
        }else {
            mainPresenter.loadMoviesFromServer(moviePrefsHelper.getSortMovie());
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        favMovieLoader = new FavMovieLoader(this);
        return favMovieLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {

       mainPresenter.loadMoviesFromDataBase(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    /**
     *
     * @param loaderManager the loadermanager to start or initialized
     */
    void startLoader(LoaderManager loaderManager){
        if(loaderManager.getLoader(FAV_MOVIE_LOADER_ID) == null){
            loaderManager.initLoader(FAV_MOVIE_LOADER_ID, null, this);
        }else {
            loaderManager.restartLoader(FAV_MOVIE_LOADER_ID, null, this);
        }

        //movieAdapter.notifyDataSetChanged();
    }
}
