package com.friends.handsheadstest;

import android.app.Application;

import com.friends.handsheadstest.api.WeatherApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static App app;
    private static WeatherApi weatherApi;
    private static final String URL = "http://api.openweathermap.org";
    public static final String API_KEY = "5677e09f29528b0091daa6fef80aae77";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);
    }

    public static App getInstance() {
        return app;
    }

    public static WeatherApi getWeatherApi() {
        return weatherApi;
    }

}
