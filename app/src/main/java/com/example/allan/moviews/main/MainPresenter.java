package com.example.allan.moviews.main;

import android.util.Log;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieResponse;
import com.example.allan.moviews.util.MoviePrefsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */

class MainPresenter <T extends MainView>  extends BasePresenter<T> {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();
    private MovieService movieService;
    private MoviePrefsHelper moviePrefsHelper;

    MainPresenter(MovieService movieService, MoviePrefsHelper moviePrefsHelper) {
        this.movieService = movieService;
        this.moviePrefsHelper = moviePrefsHelper;
    }

    /**
     * Set the movie data to be display on the recyclerview
     */
    void setMovieData(String url){
        getmMvpView().showProgress();
        Call<MovieResponse> movieResponseCall = movieService.getMovieApi()
                .getMovieResponse(url, BuildConfig.MOVIE_DB_API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                getmMvpView().hideProgress();
                getmMvpView().showListOfMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                getmMvpView().hideProgress();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }


    /**
     *
     * @param movieUrl The url string {top_rated or popular}
     */
    void setMovieUrl(String movieUrl) {
        if (movieUrl == null || movieUrl.equals("")){
            return;
        }
        //set the url from sharedPref
        moviePrefsHelper.setSortMovie(movieUrl);
    }
}
