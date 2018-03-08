package com.example.allan.moviews.model.favData;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Allan Pana on 08/03/18.
 * allan.pana74@gmail.com
 */

public class FavMovieContract{

    public static final String AUTHORITY = "com.example.allan.moviews";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAV_MOVIE = "fav_movie";

    /* FavMovieEntry is an inner class that defines the contents of the fav_movie table */
    public static final class FavMovieEntry implements BaseColumns {

        // FavMovieEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIE).build();


        // fav_movie table and column names
        public static final String TABLE_NAME = PATH_FAV_MOVIE;

        // Since FavMovieEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_NAME = "movie_name";

        public static final String COLUMN_IMAGE = "movie_image";



    }
}
