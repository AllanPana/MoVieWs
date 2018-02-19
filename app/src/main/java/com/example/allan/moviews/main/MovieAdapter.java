package com.example.allan.moviews.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.allan.moviews.R;
import com.example.allan.moviews.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Allan Pana on 19/02/18.
 *  A Recyclerview Adapter for the main activity class
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private MovieOnItemClickListener movieOnItemClickListener;
    private List<MovieItem> movies;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w185//";


    public MovieAdapter(List<MovieItem> movies, MovieOnItemClickListener movieOnItemClickListener) {
        this.movies = movies;
        this.movieOnItemClickListener = movieOnItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieItem movie = movies.get(position);
        String imageUrl = IMAGE_URL_BASE_PATH + movie.getPosterPath();

        holder.ivMoviePoster.getLayoutParams().height = getRandomIntInRange(600, 300);

        Picasso.with(holder.itemView.getContext()).load(imageUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.ivMoviePoster);

    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }


    /**
     * Use to set the height of imageview randomly
     * @param max maximum possible height of the imageview
     * @param min minimum possible height of the imageview
     * @return
     */
    protected int getRandomIntInRange(int max, int min){
        return new Random().nextInt((max-min)+min)+min;
    }


    /**
     * The ViewHolder class for the MovieAdapter to bind
     */
    class MovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_movie_poster) ImageView ivMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivMoviePoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "allan", Toast.LENGTH_LONG).show();
                    movieOnItemClickListener.onMovieItemClick(getAdapterPosition());
                    //todo
                }
            });
        }
    }

    interface MovieOnItemClickListener{
        void onMovieItemClick(int position);
    }
}
