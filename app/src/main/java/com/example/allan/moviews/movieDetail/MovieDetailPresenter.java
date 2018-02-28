package com.example.allan.moviews.movieDetail;

import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;

/**
 * Created by Allan Pana on 22/02/18.
 * allan.pana74@gmail.com
 */

class MovieDetailPresenter<T extends MovieDetailView> extends BasePresenter<T> {

    private MovieItem movieItem;

    MovieDetailPresenter(MovieItem movieItem) {
        this.movieItem = movieItem;
    }

    void setToolBar() {
        getmMvpView().displayToolBar(movieItem.getOriginalTitle());
    }

    void setAdditionalMovieDetails() {
        getmMvpView().loadImageIntoToolbar(movieItem.getBackdropPath());
        getmMvpView().loadThumbnailImage(movieItem.getPosterPath());
        getmMvpView().displayPlotSynopsis(movieItem.getOverview());
        getmMvpView().displayReleaseDate(movieItem.getReleaseDate());
        getmMvpView().displayMovieRating((movieItem.getVoteAverage() / 2) + "/5",
                (float) (movieItem.getVoteAverage() / 2));
    }
}
