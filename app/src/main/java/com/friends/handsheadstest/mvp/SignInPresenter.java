package com.friends.handsheadstest.mvp;

import com.friends.handsheadstest.R;
import com.friends.handsheadstest.models.Wheather;
import com.friends.handsheadstest.App;
import com.friends.handsheadstest.utils.Utils;

import java.util.Locale;

import rx.Observable;
import rx.Observer;

public class SignInPresenter extends BasePresenter<SignInView> implements Observer<Wheather> {

    public SignInPresenter(SignInView view) {
        attachView(view);
    }

    @Override
    public void attachView(SignInView signInView) {
        super.attachView(signInView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    //Hardcode for test)
    public void getWheather() {
        if (Utils.isNetworkAvailable()) {
            getWheatherFromApi();
        } else {
            getMainView().showErrorToast(R.string.no_internet);
        }
    }

    private void getWheatherFromApi() {
        checkViewAttached();
        getMainView().showLoading();
        Observable<Wheather> wheatherObservable = App.getWeatherApi().getWeatherCurrent("Moscow", "metric", Locale.getDefault().getLanguage(),
                App.API_KEY);
        subscribe(wheatherObservable, this);
    }

    @Override
    public void onCompleted() {
        getMainView().hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        getMainView().hideLoading();
        getMainView().showErrorToast(R.string.response_error);
    }

    @Override
    public void onNext(Wheather wheather) {
        getMainView().hideLoading();
        getMainView().showSnackBar(String.valueOf(wheather.getMain().getTemp()));
    }

}

