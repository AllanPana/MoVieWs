package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseFragment;
import com.example.allan.moviews.model.MovieItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by Allan Pana on 26/02/18.
 * allan.pana74@gmail.com
 */

public class MovieDetailFragment extends BaseFragment implements MovieDetailView{

    private static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w780//";
    private static final String MOVIE_ITEM = "movie_item";
    private MovieDetailPresenter movieDetailPresenter;
    private static MovieItem movie;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.colapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_movie_landscape) ImageView imageView;

    public static MovieDetailFragment newFragmentinstance(Context context, MovieItem movieItem){
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movie = movieItem;
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ITEM, movieItem);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;

    }

    @Override
    public void onViewCreated() {
        MovieItem movieItem = getArguments().getParcelable(MOVIE_ITEM);
        Log.e("fragment", movieItem.getTitle());

        if (imageView != null){
            Picasso.with(getActivity()).load(IMAGE_URL_BASE_PATH + movieItem.getBackdropPath())
                    .into(imageView);
        }

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    public void onStart() {
        super.onStart();
        movieDetailPresenter = new MovieDetailPresenter(movie);
        movieDetailPresenter.attachView(this);
        movieDetailPresenter.setToolBar();
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void displayToolBar(String toolBarTitle) {
        toolbar.setTitle(toolBarTitle);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout.setTitle(toolBarTitle);
    }
}
