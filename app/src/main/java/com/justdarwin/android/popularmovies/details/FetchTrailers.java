package com.justdarwin.android.popularmovies.details;


import android.os.AsyncTask;
import android.util.Log;

import com.justdarwin.android.popularmovies.BuildConfig;
import com.justdarwin.android.popularmovies.common.MovieDBService;
import com.justdarwin.android.popularmovies.common.Trailer;
import com.justdarwin.android.popularmovies.common.Trailers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by darwin on 28/07/17.
 */

public class FetchTrailers extends AsyncTask<Long, Void, List<Trailer>> {

    @SuppressWarnings("unused")
    public static String LOG_TAG = FetchTrailers.class.getSimpleName();
    private final Listener mListener;

    /**
     * Interface definition for a callback to be invoked when trailers are loaded.
     */
    interface Listener {
        void onFetchFinished(List<Trailer> trailers);
    }

    public FetchTrailers(Listener listener) {
        mListener = listener;
    }

    @Override
    protected List<Trailer> doInBackground(Long... params) {
        // If there's no movie id, there's nothing to look up.
        if (params.length == 0) {
            return null;
        }
        long movieId = params[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDBService service = retrofit.create(MovieDBService.class);
        Call<Trailers> call = service.findTrailersById(movieId,
                BuildConfig.THE_MOVIE_DATABASE_API_KEY);
        try {
            Response<Trailers> response = call.execute();
            Trailers trailers = response.body();
            return trailers.getTrailers();
        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        if (trailers != null) {
            mListener.onFetchFinished(trailers);
        } else {
            mListener.onFetchFinished(new ArrayList<Trailer>());
        }
    }
}
