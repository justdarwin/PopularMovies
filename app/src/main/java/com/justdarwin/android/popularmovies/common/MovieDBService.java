package com.justdarwin.android.popularmovies.common;

import com.justdarwin.android.popularmovies.movie.ApiResults;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by darwin on 06/07/17.
 */
public interface MovieDBService{
    static final String API_KEY = "?api_key=";
    static final String BASE_URL = "http://api.themoviedb.org/";

    @GET("3/movie/popular"+API_KEY)
    Call<ApiResults> getPopularMovies();

    @GET("3/movie/top_rated"+API_KEY)
    Call<ApiResults> getTopRatedMovies();

    public static final Retrofit ADAPTER = new Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
