package com.justdarwin.android.popularmovies.common;

import android.util.Log;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by darwin on 06/07/17.
 *
 * This class handles all network requests to The MoviesDB API
 * Retrofit is used to create the request, which will then inject the response directly to the specified POJO using GSON
 *
 * Retrofit instance is intentionally created as a static variable (singleton), because only one instance is needed (no need to instantiate
 * for every call)
 */

public class MovieDbApi {
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_KEY = "?api_key=176a31b870b03b8ed394a76567288f6a";

    private static final Retrofit ADAPTER = new Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final MovieDBService service = ADAPTER.create(MovieDBService.class);

    public static MovieDBService getService(){
        Log.d("Hasil base", BASE_URL);
        return service;
    }
}
