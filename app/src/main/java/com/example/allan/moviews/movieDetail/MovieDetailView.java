package com.example.allan.moviews.movieDetail;

import com.example.allan.moviews.base.MVPView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Allan Pana on 22/02/18.
 * allan.pana74@gmail.com
 */

interface MovieDetailView extends MVPView {


    /**
     * @param keyList list of trailers key available
     */
    void loadTrailers(List<String> keyList);

    /**
     * @param imageUrl the image  url to load by Picasso
     */
    void loadThumbnailImage(String imageUrl);

    /**
     * @param strSynopsis the plot sysnopsis of the movie
     */
    void displayPlotSynopsis(String strSynopsis);

    /**
     * @param releaseDate of the movie
     */
    void displayReleaseDate(String releaseDate);

    /**
     * @param strMovieRating String value of the movie rating
     * @param movierating    the double value of the movie rating to be set in RatingBar
     */
    void displayMovieRating(String strMovieRating, float movierating);


    void displayExpandableListForMovieDetail(List<String> expandableListTitle,
                                             HashMap<String, List<String>> expandableListDetail);
}
