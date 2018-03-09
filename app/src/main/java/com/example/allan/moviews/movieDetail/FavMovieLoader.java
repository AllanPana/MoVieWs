package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.allan.moviews.model.favData.FavMovieContract;

import java.lang.ref.WeakReference;

/**
 * Created by Allan Pana on 09/03/18.
 * allan.pana74@gmail.com
 */
class FavMovieLoader extends AsyncTaskLoader<Cursor> {

    private Cursor cursorFavMovie;
    private WeakReference<Context> context;

    FavMovieLoader(Context context) {
        super(context);
        this.context = new WeakReference<>(context);
    }

    // onStartLoading() is called when a loader first starts loading data
    @Override
    protected void onStartLoading() {
        if (cursorFavMovie != null) {
            deliverResult(cursorFavMovie); // Delivers any previously loaded data immediately
        } else {
            forceLoad(); // Force a new load
        }


    }

    // loadInBackground() performs asynchronous loading of data
    @Override
    public Cursor loadInBackground() {
        // Will implement to load data
        // Query and load all task data in the background; sort by priority
        // [Hint] use a try/catch block to catch any errors in loading data
        try {
            return context.get().getContentResolver().query(FavMovieContract.FavMovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // deliverResult sends the result of the load, a Cursor, to the registered listener
    public void deliverResult(Cursor data) {
        cursorFavMovie = data;
        super.deliverResult(data);
    }
}
