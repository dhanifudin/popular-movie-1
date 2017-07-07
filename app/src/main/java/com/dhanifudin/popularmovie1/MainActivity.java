package com.dhanifudin.popularmovie1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanifudin.popularmovie1.model.Movie;
import com.dhanifudin.popularmovie1.utilities.DummyUtils;
import com.dhanifudin.popularmovie1.utilities.JsonUtils;
import com.dhanifudin.popularmovie1.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView moviesView;
    private MovieAdapter movieAdapter;

    private TextView errorMessageText;
    private ProgressBar loadingProgress;

    private Movie[] movies;

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessageText = (TextView) findViewById(R.id.text_error);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        moviesView = (RecyclerView) findViewById(R.id.movies_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        moviesView.setLayoutManager(layoutManager);
        moviesView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        movieAdapter.setMovies(movies);
        moviesView.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            category = Constants.CATEGORY_POPULAR;
            requestMovies();
        } else {
            category = savedInstanceState.getString(Constants.CATEGORY);
            Parcelable[] parcelableMovies = savedInstanceState.getParcelableArray(Constants.MOVIES);
            if (parcelableMovies != null) {
                movies = Arrays.copyOf(parcelableMovies, parcelableMovies.length, Movie[].class);
                movieAdapter.setMovies(movies);
            } else {
                requestMovies();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        MenuItem menuItem = menu.findItem(R.id.action_category);
        updateMenuTitle(menuItem);
        return true;
    }

    public void updateMenuTitle(MenuItem menuItem) {
        if (category.equals(Constants.CATEGORY_POPULAR))
            menuItem.setTitle(getString(R.string.action_top_rated));
        else
            menuItem.setTitle(getString(R.string.action_popular));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(Constants.MOVIES, movies);
        outState.putString(Constants.CATEGORY, category);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_category:
                category = (category.equals(Constants.CATEGORY_POPULAR))
                        ? Constants.CATEGORY_TOP_RATED
                        : Constants.CATEGORY_POPULAR;
                updateMenuTitle(item);
            case R.id.action_refresh:
                requestMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void requestMovies() {
        if (isOnline()) {
            Toast.makeText(this, "Requesting " + category + " movies.", Toast.LENGTH_LONG)
                    .show();
            new TheMovieTask().execute(category);
        } else {
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void showMovieDataView() {
        errorMessageText.setVisibility(View.INVISIBLE);
        moviesView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        moviesView.setVisibility(View.INVISIBLE);
        errorMessageText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(Constants.MOVIE, movie);
        startActivity(detailIntent);
    }

    class TheMovieTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            String category = params[0];
            Movie[] movies = null;
            try {
                URL requestUrl = NetworkUtils.buildUrl(category);
                String response = NetworkUtils.getResponseFromHttpUrl(requestUrl);
                movies = JsonUtils.getMovies(response);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }

        @Override
        protected void onPostExecute(Movie[] moviesData) {
            loadingProgress.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                movies = moviesData;
                showMovieDataView();
                movieAdapter.setMovies(movies);
            } else {
                showErrorMessage();
            }
        }

    }
}
