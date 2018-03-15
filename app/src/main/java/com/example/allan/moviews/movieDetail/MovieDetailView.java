package com.example.allan.moviews.movieDetail;

import android.graphics.Bitmap;

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
     * @param releaseDate of the movie
     */
    void displayReleaseDate(String releaseDate);

    /**
     * @param strMovieRating String value of the movie rating
     * @param movierating    the double value of the movie rating to be set in RatingBar
     */
    void displayMovieRating(String strMovieRating, float movierating);


    /**
     *
     * @param expandableListTitle Group title of the ExpandableListView {Movie Reviews and Synopsis}
     * @param expandableListDetail map that hold the data for {Movie Reviews and Synopsis}
     */
    void displayExpandableListForMovieDetail(List<String> expandableListTitle,
                                             HashMap<String, List<String>> expandableListDetail);

    /**
     *
     * @return bitmap of posterpath image
     */
    Bitmap getPosterPathBitmap();

    /**
     * This method will set the image from dbmovie into bitmap to be saved into the Database
     *
     * @param url of a poster path image from DBMovie
     */
    void setPosterPathBitmap(String url);

    /**
     *
     * @return bitmap of backdrop path image
     */
    Bitmap getBackDropPathBitmap();

    /**
     * This method will set the image from dbmovie into bitmap to be saved into the Database
     *
     * @param url of a backdrop path path image from DBMovie
     */
    void setBackDropPathPathBitmap(String url);
}
