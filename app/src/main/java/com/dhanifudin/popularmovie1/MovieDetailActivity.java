package com.dhanifudin.popularmovie1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanifudin.popularmovie1.model.Movie;
import com.squareup.picasso.Picasso;

import static com.dhanifudin.popularmovie1.Constants.MOVIE;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;

    private ImageView backdropImage;
    private ImageView posterImage;
    private TextView titleText;
    private TextView originalTitleText;
    private TextView releaseText;
    private RatingBar movieRating;
    private TextView informationText;
    private TextView overviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE)) {
            Bundle bundle = getIntent().getExtras();
            movie = bundle.getParcelable(MOVIE);
        } else {
            movie = savedInstanceState.getParcelable(MOVIE);
        }

        backdropImage = (ImageView) findViewById(R.id.image_backdrop);
        titleText = (TextView) findViewById(R.id.text_title);
        posterImage = (ImageView)  findViewById(R.id.image_poster);
        originalTitleText = (TextView) findViewById(R.id.text_original_title);
        releaseText = (TextView) findViewById(R.id.text_release);
        movieRating = (RatingBar) findViewById(R.id.rating_movie);
        informationText = (TextView) findViewById(R.id.text_information);
        overviewText = (TextView) findViewById(R.id.text_overview);

        Button favoriteButton = (Button) findViewById(R.id.button_favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, "Add as favorite", Toast.LENGTH_LONG)
                        .show();
            }
        });
        displayData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(MOVIE, movie);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void displayData() {
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getBackdropPath())
                    .into(backdropImage);
            Picasso.with(this)
                    .load(movie.getPosterPath())
                    .into(posterImage);
            titleText.setText(movie.getTitle());
            String originalTitle = String.format(
                    "%s (%s)",
                    movie.getOriginalTitle(),
                    movie.getOriginalLanguage()
            );
            originalTitleText.setText(originalTitle);
            releaseText.setText(movie.getReleaseDate());
            movieRating.setRating(movie.getVoteAverage());
            String information = String.format(
                    "%.2f/10 of %d",
                    movie.getVoteAverage(),
                    movie.getVoteCount()
            );
            informationText.setText(information);
            overviewText.setText(movie.getOverview());
        }
    }
}
