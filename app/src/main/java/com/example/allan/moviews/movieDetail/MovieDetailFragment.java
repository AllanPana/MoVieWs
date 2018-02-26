package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseFragment;
import com.example.allan.moviews.model.MovieItem;

/**
 * Created by Allan Pana on 26/02/18.
 * allan.pana74@gmail.com
 */

public class MovieDetailFragment extends BaseFragment{

    private static final String MOVIE_ITEM = "movie_item";

    public static MovieDetailFragment newFragmentinstance(Context context, MovieItem movieItem){
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ITEM, movieItem);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;

    }

    @Override
    public void onViewCreated() {
        MovieItem movieItem = getArguments().getParcelable(MOVIE_ITEM);
        Log.e("fragment", movieItem.getTitle());

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_movie_detail;
    }
}
