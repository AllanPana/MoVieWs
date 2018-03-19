package com.example.allan.moviews.movieDetail;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.allan.moviews.BuildConfig;
import com.example.allan.moviews.R;
import com.example.allan.moviews.apiService.MovieService;
import com.example.allan.moviews.base.BaseFragment;
import com.example.allan.moviews.model.MovieItem;
import com.example.allan.moviews.model.favData.FavMovieContract;
import com.example.allan.moviews.util.FavMovieLoader;
import com.example.allan.moviews.util.MovieAppUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Allan Pana on 26/02/18.
 * allan.pana74@gmail.com
 */

public class MovieDetailFragment extends BaseFragment
        implements MovieDetailView{

    private static final String IMAGE_URL_POSTER_PATH = "http://image.tmdb.org/t/p/w300//";
    private static final String IMAGE_URL_BACK_DROP_PATH = "http://image.tmdb.org/t/p/w780//";
    private static final String MOVIE_ITEM = "movie_item";
    private MovieDetailPresenter movieDetailPresenter;
    private static MovieItem movie;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.colapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_thumb_nail)
    ImageView ivThumbNail;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.expandableListViewForDetails)
    ExpandableListView expandableListView;
    @BindView(R.id.tv_add_to_fav)
    TextView tvAddToFab;
    @BindView(R.id.imageView_fav)
    ImageView imageViewAddToFav;
    @BindView(R.id.iv_no_video)
    ImageView imageViewIfNoVideo;

    private Bitmap posterPathBitmap;
    private Bitmap backDropPathBitmap;
    private boolean isFav;
    private  boolean isMovieAlreadyAdded;

    public static MovieDetailFragment newFragmentinstance(MovieItem movieItem, boolean isFav) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        //movie = movieItem;
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_ITEM, movieItem);
        args.putBoolean(MovieDetailActivity.IS_FAVORITE, isFav);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;

    }

    @Override
    public void onViewCreated() {
        //Get the parcelable args(MovieItem) from the MovieDetailActivity
        Bundle args = getArguments();
        movie = args.getParcelable(MOVIE_ITEM);
        isFav = args.getBoolean(MovieDetailActivity.IS_FAVORITE, false);

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    public void onStart() {
        super.onStart();
        movieDetailPresenter = new MovieDetailPresenter(new MovieService(), movie);
        movieDetailPresenter.attachView(this);
        movieDetailPresenter.setToolBar();
        movieDetailPresenter.setAdditionalMovieDetails();

        if (isFav){
            movieDetailPresenter.setExpandableListMovieDataFromDataBase();

        }else {
            imageViewIfNoVideo.setVisibility(View.GONE);
            movieDetailPresenter.setTrailer();
            movieDetailPresenter.setExpandableListMovieDataFromServer();
        }

        isMovieAlreadyAdded = movieDetailPresenter.isMovieAlreadyAdded(getActivity().getContentResolver());

        //check if item is already added in db
        if (isFav || isMovieAlreadyAdded){
            tvAddToFab.setVisibility(View.GONE);
        }else {
            tvAddToFab.setVisibility(View.VISIBLE);
            tvAddToFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewAddToFav.animate().rotationBy(720);
                    movieDetailPresenter.addMovieToFav(getActivity().getContentResolver());
                    getActivity().finish();
                }
            });
        }


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
        //collapsingToolbarLayout.setTitle(toolBarTitle);
    }

    @Override
    public void loadTrailers(final List<String> trailerKeys) {

        YouTubePlayerSupportFragment youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.video_container, youTubePlayerSupportFragment).commit();

        youTubePlayerSupportFragment.initialize(BuildConfig.YOU_TUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                toolbar.setVisibility(View.GONE);
                youTubePlayer.cueVideos(trailerKeys);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public void loadThumbnailImage(String imageUrl) {
        if (isFav){
            ivThumbNail.setImageBitmap(MovieAppUtil.getImage(movie.getBytesPosterPath()));
        }else {
            Picasso.with(getActivity()).load(IMAGE_URL_POSTER_PATH + imageUrl)
                    .into(ivThumbNail);
        }

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

    @Override
    public void displayImageviewIfNoVideo(Bitmap bitmap) {
        imageViewIfNoVideo.setVisibility(View.VISIBLE);
        imageViewIfNoVideo.setImageBitmap(bitmap);
    }

    @Override
    public void displayExpandableListForMovieDetail(List<String> expandableListTitle,
                                                    HashMap<String, List<String>> expandableListDetail) {

        MovieDetailListAdapter listAdapter = new MovieDetailListAdapter(getActivity(),
                expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(listAdapter);
    }

    @Override
    public Bitmap getPosterPathBitmap() {
        return posterPathBitmap;
    }

    @Override
    public void setPosterPathBitmap(final String imageUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    posterPathBitmap = Picasso.with(getActivity())
                            .load(IMAGE_URL_POSTER_PATH + imageUrl)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public Bitmap getBackDropPathBitmap() {
        return backDropPathBitmap;
    }

    @Override
    public void setBackDropPathPathBitmap(final String imageUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    backDropPathBitmap = Picasso.with(getActivity())
                            .load(IMAGE_URL_BACK_DROP_PATH + imageUrl)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
