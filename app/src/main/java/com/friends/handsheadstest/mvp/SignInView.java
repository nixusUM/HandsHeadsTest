package com.friends.handsheadstest.mvp;

public interface SignInView extends MainView {
    void showSnackBar(String temperature);
    void showLoading();
    void hideLoading();
    void showErrorToast(int message);
}
