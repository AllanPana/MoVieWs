package com.example.allan.moviews.movieDetail;

import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;

/**
 * Created by Allan Pana on 22/02/18.
 * allan.pana74@gmail.com
 */

public class MovieDetailPresenter<T extends MovieDetailView> extends BasePresenter<T> {

    private MovieItem movieItem;

    public MovieDetailPresenter(MovieItem movieItem) {
        this.movieItem = movieItem;
    }


    void setToolBar(){
        getmMvpView().displayToolBar(movieItem.getTitle());
    }

}
