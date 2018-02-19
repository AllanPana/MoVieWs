package com.example.allan.moviews.main;

import com.example.allan.moviews.base.MVPView;
import com.example.allan.moviews.model.MovieItem;

import java.util.List;

/**
 * Created by Allan Pana on 19/02/18.
 */

public interface MainView extends MVPView {

    void showListOfMovies(List<MovieItem> results);
}
