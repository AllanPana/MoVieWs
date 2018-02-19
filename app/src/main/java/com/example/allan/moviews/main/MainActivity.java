package com.example.allan.moviews.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Allan Pana on 19/02/18.
 * 
 */
public class MainActivity extends BaseActivity implements MainView {

    private MainPresenter mainPresenter;
    @BindView(R.id.rv_movie) RecyclerView recyclerView;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        mainPresenter.setMovieData();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
    }

    @Override
    public void showProgress() {
        //TODO
    }

    @Override
    public void hideProgress() {
        //TODO
    }

    @Override
    public void showListOfMovies(List<MovieItem> results) {
        Log.e("allan", results.get(0).getTitle());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(new MovieAdapter(results));
    }
}
