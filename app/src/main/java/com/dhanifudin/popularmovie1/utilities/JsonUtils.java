package com.dhanifudin.popularmovie1.utilities;

import com.dhanifudin.popularmovie1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class JsonUtils {

    static final String MOVIE_RESULTS = "results";

    static final String MOVIE_VOTE_COUNT = "vote_count";
    static final String MOVIE_VOTE_AVERAGE = "vote_average";
    static final String MOVIE_ID = "id";
    static final String MOVIE_VIDEO = "video";
    static final String MOVIE_TITLE = "title";
    static final String MOVIE_POPULARITY = "popularity";
    static final String MOVIE_POSTER_PATH = "poster_path";
    static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    static final String MOVIE_ORIGINAL_TITLE = "original_title";
    static final String MOVIE_BACKDROP_PATH = "backdrop_path";
    static final String MOVIE_ADULT = "adult";
    static final String MOVIE_OVERVIEW = "overview";
    static final String MOVIE_RELEASE_DATE = "release_date";

    public static Movie[] getMovies(String json) throws JSONException {
        Movie[] movies = null;
        JSONObject moviesJson = new JSONObject(json);
        if (moviesJson.has(MOVIE_RESULTS)) {
            JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_RESULTS);
            movies = new Movie[moviesArray.length()];

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieJson = moviesArray.getJSONObject(i);
                Movie movie = new Movie();
                int id = movieJson.getInt(MOVIE_ID);
                movie.setId(id);

                String title = movieJson.getString(MOVIE_TITLE);
                movie.setTitle(title);

                String originalTitle = movieJson.getString(MOVIE_ORIGINAL_TITLE);
                movie.setOriginalTitle(originalTitle);

                String overview = movieJson.getString(MOVIE_OVERVIEW);
                movie.setOverview(overview);

                String originalLanguage = movieJson.getString(MOVIE_ORIGINAL_LANGUAGE);
                movie.setOriginalLanguage(originalLanguage);

                String releaseDate = movieJson.getString(MOVIE_RELEASE_DATE);
                movie.setReleaseDate(releaseDate);

                int voteCount = movieJson.getInt(MOVIE_VOTE_COUNT);
                movie.setVoteCount(voteCount);

                double voteAverage = movieJson.getDouble(MOVIE_VOTE_AVERAGE);
                movie.setVoteAverage((float) voteAverage);

                double popularity = movieJson.getDouble(MOVIE_POPULARITY);
                movie.setPopularity((float) popularity);

                String posterPath = movieJson.getString(MOVIE_POSTER_PATH);
                movie.setPosterPath(posterPath);

                String backdropPath = movieJson.getString(MOVIE_BACKDROP_PATH);
                movie.setBackdropPath(backdropPath);

                boolean video = movieJson.getBoolean(MOVIE_VIDEO);
                movie.setVideo(video);

                boolean adult = movieJson.getBoolean(MOVIE_ADULT);
                movie.setAdult(adult);

                movies[i] = movie;
            }
        }
        return movies;
    }
}
