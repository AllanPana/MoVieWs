package com.example.allan.moviews.model.favData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.allan.moviews.model.favData.FavMovieContract.*;

/**
 * Created by Allan Pana on 08/03/18.
 * allan.pana74@gmail.com
 */

public class FavMovieDb extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "favMovieDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 4;


    public FavMovieDb(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + FavMovieEntry.TABLE_NAME + " (" +
                FavMovieEntry._ID            + " INTEGER PRIMARY KEY, " +
                FavMovieEntry.COLUMN_POSTER_PATH + " BLOB, " +
                FavMovieEntry.COLUMN_BACK_DROP_PATH + " BLOB, " +
                FavMovieEntry.COLUMN_SYNOPSIS + " TEXT, " +
                FavMovieEntry.COLUMN_REVIEW + " TEXT, " +
                FavMovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                FavMovieEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
                FavMovieEntry.COLUMN_NAME    + ");";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavMovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
