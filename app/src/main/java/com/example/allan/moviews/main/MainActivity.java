package com.example.allan.moviews.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseActivity;
import com.example.allan.moviews.model.MovieItem;

import java.util.List;

import butterknife.BindView;


/**
 * Created by Allan Pana on 19/02/18.
 * 
 */
public class MainActivity extends BaseActivity implements MainView, MovieAdapter.MovieOnItemClickListener {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.action_movie_popular){
            Log.e("allan", item.getTitle().toString());
        }else if (itemID == R.id.action_movie_top_rated){
            Log.e("allan", item.getTitle().toString());
        }
        return super.onOptionsItemSelected(item);
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
        recyclerView.setAdapter(new MovieAdapter(results, this));
    }

    @Override
    public void onMovieItemClick(int position) {
        //todo

    }
}
