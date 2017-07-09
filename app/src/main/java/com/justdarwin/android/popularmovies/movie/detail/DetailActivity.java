package com.justdarwin.android.popularmovies.movie.detail;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.justdarwin.android.popularmovies.R;
import com.justdarwin.android.popularmovies.movie.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

/**
 * Created by darwin on 10/07/17.
 */

public class DetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView Title = (TextView) findViewById(R.id.detail_title);
        ImageView Poster = (ImageView) findViewById(R.id.detail_poster);
        TextView Overview = (TextView) findViewById(R.id.detail_overview);
        TextView Averagevote = (TextView) findViewById(R.id.detail_average_vote);
        TextView Releasedate = (TextView) findViewById(R.id.detail_release_date);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("hore");

        Title.setText(movie.getOriginalTitle());

        Picasso.with(this).load(movie.getPosterPath()).into(Poster);

        String overView = movie.getOverview();
        if (overView == null) {
            Overview.setTypeface(null, Typeface.ITALIC);
            overView =("No film review yet");
        }
        Overview.setText(overView);
        Averagevote.setText(movie.getVoteAverage().toString());

        // First get the release date from the object - to be used if something goes wrong with
        // getting localized release date (catch).
        String releaseDate = movie.getReleaseDate();
        if(releaseDate != null) {
                releaseDate = movie.getReleaseDate().toString();
        } else {
            Releasedate.setTypeface(null, Typeface.ITALIC);
            releaseDate = ("No Release date yet");
        }
        Releasedate.setText(releaseDate);
    }
}
