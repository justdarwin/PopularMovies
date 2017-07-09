package com.justdarwin.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.justdarwin.android.popularmovies.common.MovieDBService;
import com.justdarwin.android.popularmovies.common.MovieDbApi;
import com.justdarwin.android.popularmovies.movie.ApiResults;
import com.justdarwin.android.popularmovies.movie.Movie;
import com.justdarwin.android.popularmovies.movie.detail.DetailActivity;
import com.justdarwin.android.popularmovies.movie.listing.CustomItemClickListener;
import com.justdarwin.android.popularmovies.movie.listing.CustomRecyclerTouchListener;
import com.justdarwin.android.popularmovies.movie.listing.MoviesListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void callService(String type){
        if(isNetworkAvailable()){
            MovieDBService movieDBService = MovieDbApi.getService();
            Call<ApiResults> call = null;

            if(type.equals("popular")){
                call = movieDBService.getPopularMovies();
            }else{
                call = movieDBService.getTopRatedMovies();
            }

            new NetworkCall().execute(call);
        } else {
                Toast.makeText(this, "Please, connect to the internet" , Toast.LENGTH_LONG).show();
        }
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.poster_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new CustomRecyclerTouchListener(this,
                recyclerView, new CustomItemClickListener() {

            @Override
            public void onClick(View view, final int position) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("hore", movie);

                startActivity(intent);
            }
        }));
        callService("popular");
    }

    private void updateAdapter(List<Movie> movies){
        recyclerView.setAdapter(new MoviesListAdapter(getApplicationContext(), movies));
    }


    private class NetworkCall extends AsyncTask<Call, Void, ApiResults>{
        @Override
        protected ApiResults doInBackground (Call... params){
            try{
                Call<ApiResults> call = params[0];
                Response<ApiResults> response = call.execute();
                return response.body();
            } catch (IOException e){
                e.printStackTrace();
            }
          return null;
        }

        @Override
        protected void onPostExecute (ApiResults results){
            movies = results.getResults();
            updateAdapter(movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.popular:
                callService("popular");
                return true;
            case R.id.topRated:
                callService("top");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}