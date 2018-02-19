package com.example.cristina.moviews.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Allan Pana on 19/02/18.
 */

public abstract class BaseFragment extends Fragment {

    public Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

    }
}
