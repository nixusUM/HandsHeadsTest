package com.friends.handsheadstest.mvp;

public interface Presenter<V extends MainView> {

    void attachView(V mainView);
    void detachView();
}
