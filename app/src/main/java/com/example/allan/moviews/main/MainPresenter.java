package com.example.allan.moviews.main;

import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.allan.moviews.apiService.MovieService.API_KEY;

/**
 * Created by Allan Pana on 19/02/18.
 */

public class MainPresenter <T extends MainView>  extends BasePresenter<T> {

    private MovieService movieService;
    private static final String TOP_RATED = "movie/top_rated";
    private static final String MOST_POPULAR = "movie/popular";

    public MainPresenter() {
        this.movieService = new MovieService();
    }

    protected void setMovieData(){

//        movieService.getMovieApi().getTopRatedMovies(MovieService.API_KEY);
        Call<MovieResponse> movieResponseCall = movieService.getMovieApi().getTopRatedMovies(TOP_RATED, API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                getmMvpView().showListOfMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //todo
            }
        });
    }
}
