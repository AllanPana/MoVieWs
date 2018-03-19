package com.example.allan.moviews.main;

import android.database.Cursor;
import android.util.Log;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BasePresenter;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.MovieResponse;
import com.example.allan.moviews.model.Review;
import com.example.allan.moviews.model.favData.FavMovieContract;
import com.example.allan.moviews.util.MovieAppUtil;
import com.example.allan.moviews.util.MoviePrefsHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 */

class MainPresenter<T extends MainView> extends BasePresenter<T> {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();
    private static final String REPLACE_TOOL_BAR_TITLE = "movie/";
    private MovieService movieService;
    private MoviePrefsHelper moviePrefsHelper;
    private List<MovieItem> movieItems;

    MainPresenter(MovieService movieService, MoviePrefsHelper moviePrefsHelper) {
        this.movieService = movieService;
        this.moviePrefsHelper = moviePrefsHelper;
    }


    //List of movie from server
    void loadMoviesFromServer(String url) {
        getmMvpView().showProgress();
        Call<MovieResponse> movieResponseCall = movieService.getMovieApi()
                .getMovieResponse(url, BuildConfig.MOVIE_DB_API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                getmMvpView().hideProgress();
                movieItems = response.body().getResults();
                getmMvpView().showNoDataAddedYet(movieItems);
                getmMvpView().showListOfMovies(movieItems);
                //Log.e("allan", movieItems.get(0).getBackdropPath());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                getmMvpView().hideProgress();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    //List of movies from db
    void loadMoviesFromDataBase(Cursor data) {
        List<MovieItem>favMovies = new ArrayList<>();

        if (data.getCount() > 0) {
            while (data.moveToNext()) {
                String name = data.getString(
                        data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_NAME));
                int movieId = data.getInt(
                        data.getColumnIndex(FavMovieContract.FavMovieEntry._ID));
                byte[] bytesPosterPath = data.getBlob(data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_POSTER_PATH));
                byte[] bytesBackDropPath = data.getBlob(data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_BACK_DROP_PATH));
                String synopsis = data.getString(
                        data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_SYNOPSIS));
                String review = data.getString(
                        data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_REVIEW));
                double voteAverage = data.getDouble(data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_VOTE_AVERAGE));
                String releaseDate = data.getString(
                        data.getColumnIndex(FavMovieContract.FavMovieEntry.COLUMN_RELEASE_DATE));

                //Create movieItem object came from db
                MovieItem movieItem = new MovieItem();
                movieItem.setOriginalTitle(name);
                movieItem.setId(movieId);
                movieItem.setBytesPosterPath(bytesPosterPath); //has to be converted to Bitmap to be used
                movieItem.setBytesBackDropPath(bytesBackDropPath); //has to be converted to Bitmap to be used
                movieItem.setOverview(synopsis);
                movieItem.setStrReview(review); //has to be converted to ArrayList to be used
                movieItem.setVoteAverage(voteAverage);
                movieItem.setReleaseDate(releaseDate);

                favMovies.add(movieItem);
                Log.e("provider", name + " = " + movieId + " \n poster = " + bytesPosterPath.length + "\n backdrop = " + bytesBackDropPath.length
                + "\n release date = " + releaseDate);

            }
        }
        movieItems = favMovies;
        getmMvpView().showNoDataAddedYet(movieItems);
        getmMvpView().showListOfMovies(movieItems);
    }


    /**
     * Set the toolbar for every sort of movie to be display
     *
     * @param title The title of the toolbar to set
     *//**/
    void setToolBar(String title) {
        getmMvpView().displayToolBar
                (title.replace(REPLACE_TOOL_BAR_TITLE, "").toUpperCase());
    }


    /**
     * @param position item index in the list of movies
     * @return Movie item from the list of movies
     */
    MovieItem getSelectedMovie(int position) {
        return movieItems.get(position);
    }


    /**
     * Set the url of the movie
     *
     * @param movieUrl The url string {top_rated or popular}
     */
    void setMovieUrl(String movieUrl) {
        if (movieUrl == null || movieUrl.equals("")) {
            return;
        }
        //set the url from sharedPref
        moviePrefsHelper.setSortMovie(movieUrl);
    }
}
