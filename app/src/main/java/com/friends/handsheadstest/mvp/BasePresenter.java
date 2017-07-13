package com.friends.handsheadstest.mvp;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BasePresenter<V extends MainView> implements Presenter<V> {

    private V mainView;

    @Override
    public void attachView(V view) {
        mainView = view;
    }

    @Override
    public void detachView() {
        mainView = null;
    }

    public boolean isViewAttached() {
        return mainView != null;
    }

    public V getMainView() {
        return mainView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .toSingle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}