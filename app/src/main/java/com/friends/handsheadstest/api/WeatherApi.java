package com.friends.handsheadstest.api;

import com.friends.handsheadstest.models.Wheather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Observable<Wheather> getWeatherCurrent(@Query("q") String city, @Query("units") String units, @Query("lang") String lang, @Query("appid") String apiKey);
}