package com.justdarwin.android.popularmovies.movie.listing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.justdarwin.android.popularmovies.R;
import com.justdarwin.android.popularmovies.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by darwin on 09/07/17.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {
    private List<Movie> daftar;
    private Context context;
    private static CustomItemClickListener itemListener;


    public MoviesListAdapter(Context context, List<Movie> daftar) {
        this.daftar = daftar;
        this.context = context;
        this.itemListener = itemListener;
    }

    @Override
    public MoviesListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poster_layout, viewGroup, false);
        final ViewHolder mViewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesListAdapter.ViewHolder holder, int position) {
        holder.properties = daftar.get(position);
        Picasso.with(context).load(daftar.get(position).getPosterPath()).into(holder.img_movies);
    }

    @Override
    public int getItemCount() {
        return daftar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Movie properties;
        private ImageView img_movies;
        public ViewHolder(View view) {
            super(view);
            img_movies = view.findViewById(R.id.img_movie);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
