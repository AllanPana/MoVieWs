package com.example.allan.moviews.movieDetail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseFragment;
import com.example.allan.moviews.model.MovieItem;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by Allan Pana on 26/02/18.
 * allan.pana74@gmail.com
 */

public class MovieDetailFragment extends BaseFragment implements MovieDetailView {

    private static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w780//";
    private static final String IMAGE_URL_BASE_PATH_THUMBNAIL = "http://image.tmdb.org/t/p/w185//";
    private static final String MOVIE_ITEM = "movie_item";
    private MovieDetailPresenter movieDetailPresenter;
    private static MovieItem movie;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.colapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_movie_landscape)
    ImageView ivLandScape;
    @BindView(R.id.iv_thumb_nail)
    ImageView ivThumbNail;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    public static MovieDetailFragment newFragmentinstance(MovieItem movieItem) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        //movie = movieItem;
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ITEM, movieItem);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;

    }

    @Override
    public void onViewCreated() {
        //Get the parcelable args(MovieItem) from the MovieDetailActivity
        movie = getArguments().getParcelable(MOVIE_ITEM);
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
        movieDetailPresenter.setAdditionalMovieDetails();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void displayToolBar(String toolBarTitle) {
        toolbar.setTitle(toolBarTitle);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        collapsingToolbarLayout.setTitle(toolBarTitle);
    }

    @Override
    public void loadImageIntoToolbar(String imageUrl) {
        Picasso.with(getActivity()).load(IMAGE_URL_BASE_PATH + imageUrl)
                .into(ivLandScape);
    }

    @Override
    public void loadThumbnailImage(String imageUrl) {
        Picasso.with(getActivity()).load(IMAGE_URL_BASE_PATH_THUMBNAIL + imageUrl)
                .into(ivThumbNail);
    }

    @Override
    public void displayPlotSynopsis(String strSynopsis) {
        tvSynopsis.setText(strSynopsis);
    }

    @Override
    public void displayReleaseDate(String releaseDate) {
        tvReleaseDate.setText(releaseDate);
    }

    @Override
    public void displayMovieRating(String strMovieRating, float movieRating) {
        tvRating.setText((strMovieRating));
        ratingBar.setRating(movieRating);
    }
}
