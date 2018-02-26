package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView{

    private static final String MOVIE_ITEM = "movie_item";
    private MovieDetailPresenter movieDetailPresenter;
    private static MovieItem movie;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
       addFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void setPresenter() {
        movieDetailPresenter = new MovieDetailPresenter(movie);
        movieDetailPresenter.attachView(this);
        movieDetailPresenter.setToolBar();

    }

    /**
     *
     * @param context The activity context
     * @param movieItem the MovieItem object that pass as Bundle from the MainActivity
     * @return an Intent to be start {MovieDetailActivity}
     */
    public static Intent getIntent(Context context, MovieItem movieItem){
        movie = movieItem;
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ITEM, movieItem);
        Log.e("allan", movieItem.getTitle());
        return intent;
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    public void displayToolBar(String toolBarTitle) {
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(toolBarTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_movie_detail,
                MovieDetailFragment.newFragmentinstance(this, movie));
        fragmentTransaction.commit();
    }
}
