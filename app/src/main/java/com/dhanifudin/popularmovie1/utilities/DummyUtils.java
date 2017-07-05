package com.dhanifudin.popularmovie1.utilities;

import android.util.Log;

import com.dhanifudin.popularmovie1.model.Movie;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class DummyUtils {

    public static Movie[] getMovies() {
        Movie[] movies = new Movie[2];
        for (int i = 0; i < 2; i++) {
            Movie movie = new Movie();
            movie.setTitle("Beauty and The Beast");
            movie.setPosterPath("/tWqifoYuwLETmmasnGHO7xBjEtt.jpg");
            Log.i("Dummy", movie.getPosterPath());
            movie.setBackdropPath("/6aUWe0GSl69wMTSWWexsorMIvwU.jpg");
            movies[i] = movie;
        }
        return movies;
    }
}
