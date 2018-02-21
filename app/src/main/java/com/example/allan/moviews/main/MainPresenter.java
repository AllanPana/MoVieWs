package com.example.allan.moviews.main;

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

public class MainPresenter <T extends MainView>  extends BasePresenter<T> {

    private MovieService movieService;
    private MoviePrefsHelper moviePrefsHelper;

    public MainPresenter(MovieService movieService, MoviePrefsHelper moviePrefsHelper) {
        this.movieService = movieService;
        this.moviePrefsHelper = moviePrefsHelper;
    }

    protected void setMovieData(){

        String url = moviePrefsHelper.getSortMovie();
        if (url == null || url.equals("")){
            return;
        }
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
                //todo
                getmMvpView().hideProgress();
            }
        });
    }


    /**
     *
     * @param movieUrl The url string {top_rated or popular}
     */
    public void setMovieUrl(String movieUrl) {

        //set the url from sharedPref
        moviePrefsHelper.setSortMovie(movieUrl);
    }
}
