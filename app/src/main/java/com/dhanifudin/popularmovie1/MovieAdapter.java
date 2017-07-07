package com.dhanifudin.popularmovie1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhanifudin.popularmovie1.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Movie[] movies;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie_list, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies[position];
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        ImageView movieImage;
        TextView titleText;

        public MovieViewHolder(View view) {
            super(view);
            context = view.getContext();
            movieImage = (ImageView) view.findViewById(R.id.image_movie);
            titleText = (TextView) view.findViewById(R.id.text_title);
            view.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.with(context)
                    .load(movie.getPosterPath())
                    .into(movieImage);
            titleText.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies[position];
            clickHandler.onClick(movie);
        }
    }
}
