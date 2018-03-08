package com.example.allan.moviews.model.favData;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Allan Pana on 08/03/18.
 * allan.pana74@gmail.com
 */

public class FavMovieContentProvider extends ContentProvider{

    private FavMovieDb movieDb;

    public static final int FAV_MOVIE = 100;
    public static final int FAV_MOVIE_WITH_ID = 101;

    // CDeclare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
         * Add matches for the fav movie directory and a single item by ID.
         */
        uriMatcher.addURI(FavMovieContract.AUTHORITY, FavMovieContract.PATH_FAV_MOVIE, FAV_MOVIE);
        uriMatcher.addURI(FavMovieContract.AUTHORITY, FavMovieContract.PATH_FAV_MOVIE + "/#", FAV_MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDb = new FavMovieDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
