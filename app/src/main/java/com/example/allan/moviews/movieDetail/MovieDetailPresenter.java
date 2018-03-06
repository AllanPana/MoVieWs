package com.example.allan.moviews.movieDetail;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.Review;
import com.example.allan.moviews.model.ReviewResponse;
import com.example.allan.moviews.model.Trailer;
import com.example.allan.moviews.model.TrailerResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allan Pana on 22/02/18.
 * allan.pana74@gmail.com
 */

class MovieDetailPresenter<T extends MovieDetailView> extends BasePresenter<T> {

    private static final String PLOT_SYNOPSIS = "PLOT SYNOPSIS";
    private static final String MOVIE_REVIEWS = "REVIEWS";
    private static final String NO_REVIEWS = "Sorry, No movie review available at the moment";
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

    /**
     * Set the hashmap value of Synopsis and Reviews details to be used for ExpandableListView
     */
    void setExpandableListMovieData() {
        final HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        final List<String> reviewList = new ArrayList<>();
        List<String> sysnopsisList = new ArrayList<>();
        sysnopsisList.add(movieItem.getOverview());
        expandableListDetail.put(PLOT_SYNOPSIS, sysnopsisList);

        Call<ReviewResponse> reviewResponseCall = movieService.getMovieApi()
                .getReviewResponse(movieItem.getId(), BuildConfig.MOVIE_DB_API_KEY);
        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                List<Review> list = response.body().getResults();
                for (Review review : list) {
                    reviewList.add(review.getAuthor()
                            + "\n\n" + review.getContent());
                }

                if (list.isEmpty()) {
                    reviewList.add(NO_REVIEWS);
                }

                expandableListDetail.put(MOVIE_REVIEWS, reviewList);
                List<String> sortedList = new ArrayList<>(expandableListDetail.keySet());
                Collections.sort(sortedList);
                getmMvpView().displayExpandableListForMovieDetail(
                        sortedList,
                        expandableListDetail);
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }
}
