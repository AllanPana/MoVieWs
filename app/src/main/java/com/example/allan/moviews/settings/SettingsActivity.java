package com.example.allan.moviews.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.allan.moviews.R;
import com.example.allan.moviews.base.BaseActivity;

//todo look for SingleFragmentActivity?

public class SettingsActivity extends BaseActivity {

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }


    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void setPresenter() {
        //todo
    }
}
