package com.example.allan.moviews.main;

import android.util.Log;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.MovieResponse;
import com.example.allan.moviews.util.MoviePrefsHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */

class MainPresenter<T extends MainView> extends BasePresenter<T> {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();
    private static final String REPLACE_TOOL_BAR_TITLE = "movie/";
    private MovieService movieService;
    private MoviePrefsHelper moviePrefsHelper;
    private List<MovieItem> movieItems;

    MainPresenter(MovieService movieService, MoviePrefsHelper moviePrefsHelper) {
        this.movieService = movieService;
        this.moviePrefsHelper = moviePrefsHelper;
    }

    /**
     * Set the movie data to be display on the recyclerview
     */
    void setMovieData(String url) {
        getmMvpView().showProgress();
        Call<MovieResponse> movieResponseCall = movieService.getMovieApi()
                .getMovieResponse(url, BuildConfig.MOVIE_DB_API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                getmMvpView().hideProgress();
                movieItems = response.body().getResults();
                getmMvpView().showListOfMovies(movieItems);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                getmMvpView().hideProgress();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }


    /**
     * Set the toolbar for every sort of movie to be display
     *
     * @param title The title of the toolbar to set
     *//**/
    void setToolBar(String title) {
        getmMvpView().displayToolBar
                (title.replace(REPLACE_TOOL_BAR_TITLE, "").toUpperCase());
    }


    /**
     * @param position item index in the list of movies
     * @return Movie item from the list of movies
     */
    MovieItem getSelectedMovie(int position) {
        return movieItems.get(position);
    }


    /**
     * Set the url of the movie
     *
     * @param movieUrl The url string {top_rated or popular}
     */
    void setMovieUrl(String movieUrl) {
        if (movieUrl == null || movieUrl.equals("")) {
            return;
        }
        //set the url from sharedPref
        moviePrefsHelper.setSortMovie(movieUrl);
    }
}
