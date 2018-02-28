package com.example.allan.moviews.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Allan Pana on 19/02/18.
 * allan.pana74@gmail.com
 * <p>
 * This class is the base Acivity to be extend by all Activity in this app
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        onActivityCreated(savedInstanceState);

    }


    @Override
    protected void onStart() {
        super.onStart();
        setPresenter();
    }

    /**
     * In this method the initialization of view
     *
     * @param savedInstanceState the state to be saved incase of configuration changes
     */
    protected abstract void onActivityCreated(Bundle savedInstanceState);

    /**
     * @return The layout of extending activity
     */
    protected abstract int getLayout();

    /**
     * Every activity that extends this base Activity has to set the presenter
     */
    public abstract void setPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
