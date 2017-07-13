package com.friends.handsheadstest.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.friends.handsheadstest.App;

public class Utils {

    public static boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
