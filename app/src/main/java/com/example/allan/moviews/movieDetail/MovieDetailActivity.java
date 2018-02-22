package com.example.allan.moviews.movieDetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseActivity;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView{

    MovieDetailPresenter movieDetailPresenter;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public void setPresenter() {
        movieDetailPresenter = new MovieDetailPresenter();
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        return intent;
    }

    @Override
    public void showProgress() {}

    @Override
    public void hideProgress() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
