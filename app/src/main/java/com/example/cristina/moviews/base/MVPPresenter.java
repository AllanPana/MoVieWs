package com.example.cristina.moviews.base;

/**
 * Created by Allan Pana on 19/02/18.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */

public interface MVPPresenter <T extends MVPView>{

    void attachView(T mVPView);

    void detachView();
}
