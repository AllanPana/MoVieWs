package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.allan.moviews.R;
import com.example.allan.moviews.model.MovieItem;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_ITEM = "movie_item";
    private static final String IS_FAVORITE = "isFav";
    private static MovieItem movie;
    private static boolean isfav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        addFragment();

        Log.e("isFav", String.valueOf(isfav));
    }

    /**
     * @param context   The activity context
     * @param movieItem the MovieItem object that pass as Bundle from the MainActivity
     * @return an Intent to be start {MovieDetailActivity}
     */
    public static Intent getIntent(Context context, MovieItem movieItem, boolean isfavorite) {
        movie = movieItem;
        isfav = isfavorite;
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ITEM, movieItem);
        intent.putExtra(IS_FAVORITE, isfavorite);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * Fragment transaction
     */
    private void addFragment() {
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newFragmentinstance(movie, isfav);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_movie_detail, movieDetailFragment);
        fragmentTransaction.commit();
    }

}
