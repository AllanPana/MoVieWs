package com.example.allan.moviews.util;

import android.content.SharedPreferences;

/**
 * Created by Allan Pana on 20/02/18.
 * allan.pana74@gmail.com
 */

public class MoviePrefsHelper  {

    public static final String MOVIE_PREFS = "movie_prefs";
    public static final String SORT_MOVIE = "sort_movie";
    private static final String POPULAR = "movie/popular";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MoviePrefsHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    /**
     * Remove all the preference
     */
    public void clear(){
        editor.clear().apply();
    }

    /**
     *
     * @param sortMovie string value tto be saved in SharedPref
     */
    public void setSortMovie(String sortMovie){
        editor.putString(SORT_MOVIE, sortMovie).apply();
    }

    public String getSortMovie(){
        return sharedPreferences.getString(SORT_MOVIE, POPULAR);
    }
}
