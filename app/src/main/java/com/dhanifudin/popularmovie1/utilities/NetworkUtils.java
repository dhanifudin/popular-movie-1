package com.dhanifudin.popularmovie1.utilities;

import android.net.Uri;

import com.dhanifudin.popularmovie1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class NetworkUtils {

    private final static String TMDB_API_KEY = BuildConfig.THEMOVIEDB_API_KEY;
    private final static String TMDB_BASE_URL = "http://api.themoviedb.org";

    private final static String PATH_VERSION = "3";
    private final static String PATH_RESOURCES = "movie";
    private final static String PARAM_API_KEY = "api_key";

    public static URL buildUrl(String category) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendPath(PATH_VERSION)
                .appendPath(PATH_RESOURCES)
                .appendPath(category)
                .appendQueryParameter(PARAM_API_KEY, TMDB_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream input = urlConnection.getInputStream();

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}

