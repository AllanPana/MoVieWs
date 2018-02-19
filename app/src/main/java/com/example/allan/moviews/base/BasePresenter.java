package com.example.allan.moviews.base;

/**
 * Created by Allan Pana on 19/02/18.
 */

public class BasePresenter<T extends MVPView> implements MVPPresenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mVPView) {
        this.mMvpView = mVPView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public T getmMvpView() {
        return mMvpView;
    }
}
