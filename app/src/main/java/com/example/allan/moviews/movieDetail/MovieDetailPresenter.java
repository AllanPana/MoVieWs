package com.example.allan.moviews.movieDetail;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.Trailer;
import com.example.allan.moviews.model.TrailerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allan Pana on 22/02/18.
 * allan.pana74@gmail.com
 */

class MovieDetailPresenter<T extends MovieDetailView> extends BasePresenter<T> {

    private MovieItem movieItem;
    private MovieService movieService;


    MovieDetailPresenter(MovieService movieService, MovieItem movieItem) {
        this.movieService = movieService;
        this.movieItem = movieItem;
    }

    void setToolBar() {
        getmMvpView().displayToolBar(movieItem.getOriginalTitle());
    }

    void setAdditionalMovieDetails() {
        getmMvpView().loadThumbnailImage(movieItem.getPosterPath());
        getmMvpView().displayPlotSynopsis(movieItem.getOverview());
        getmMvpView().displayReleaseDate(movieItem.getReleaseDate());
        getmMvpView().displayMovieRating((movieItem.getVoteAverage() / 2) + "/5",
                (float) (movieItem.getVoteAverage() / 2));
    }

    /**
     * Make an http get call to get a Trailer response
     */
    void setTrailer() {
        final List<String> stringKeys = new ArrayList<>();
        Call<TrailerResponse> trailerResponseCall = movieService.getMovieApi()
                .getTrailerResponse(movieItem.getId(), BuildConfig.MOVIE_DB_API_KEY);

        trailerResponseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {

                List<Trailer> trailers = response.body().getResults();
                for (Trailer t : trailers) {
                    stringKeys.add(t.getKey());
                }
                getmMvpView().loadTrailers(stringKeys);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });

    }
}
