package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pratyusha on 20/04/2016.
 */
public class FetchSearchResults extends AsyncTask<String, Void, ArrayList<MovieObj>> {
    SharedPreferences pref;
    Context context;
    public ArrayList<MovieObj> movieResults;
    public GridViewAdapter searchAdapter;
    GridView gridView;

    Uri buildUri;

    public FetchSearchResults(Context c, GridView lv,ArrayList<MovieObj> mR,  SharedPreferences spref ) {
        super();
        context = c;
        gridView = lv;
        movieResults=mR;
        pref=spref;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!movieResults.isEmpty()) {
            movieResults.clear();
        }


    }

    @Override
    protected ArrayList<MovieObj> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

            HttpURLConnection client = null;
            BufferedReader bufferedReader = null;
            String searchJSONstr = null;
            final String SEARCH_BASE_URL =
                    "http://api.themoviedb.org/3/";
            final String API_KEY = context.getString(R.string.api_key);
            final String API_KEY_PARAM = "api_key";
            final String MOVIE_SEGMENT = "movie";
            final String DISCOVER_QUERY_PARAM = "discover";
            String sort;
            sort = pref.getString("sort", "popular");

            buildUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                    .appendPath(MOVIE_SEGMENT)
                    .appendPath(sort)
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            URL url = null;
            try {
                url = new URL(buildUri.toString());
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            Log.d("URL", url.toString());
            try {
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                client.connect();
                InputStream inputStream = client.getInputStream();
                if (inputStream == null)
                    return null;
                StringBuilder buffer = new StringBuilder();


                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }

                if (buffer.length() == 0)
                    return null;
                searchJSONstr = buffer.toString();
                Log.d("JSON Str", searchJSONstr);
            } catch (ProtocolException e2) {
                e2.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (client != null) {
                    client.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e("Main", "Error closing stream", e);
                    }
                }
            }
            try

            {
                return getSearchDataFromJson(searchJSONstr);
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }

            return null;
        }


        //brackets secure
        private ArrayList<MovieObj> getSearchDataFromJson(String searchJSONstr)
        throws JSONException {
            final String LIST_NAME = "results";
            final String MOVIE_TITLE = "original_title";
            final String MOVIE_YEAR = "release_date";
            final String MOVIE_POSTER_URL = "poster_path";
            final String MOVIE_PLOT = "overview";
            final String MOVIE_ID = "id";
            final String VOTE_AVG = "vote_average";
            final String BG_URL = "backdrop_path";

            JSONObject searchResult = new JSONObject(searchJSONstr);
            JSONArray movieArray = searchResult.getJSONArray(LIST_NAME);
            Log.d("movieArray", movieArray.toString());


            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObject = movieArray.getJSONObject(i);
                int id = movieObject.getInt(MOVIE_ID);
                String title = movieObject.getString(MOVIE_TITLE);
                String posterUrl = movieObject.getString(MOVIE_POSTER_URL);
                String year = movieObject.getString(MOVIE_YEAR);
                String plot = movieObject.getString(MOVIE_PLOT);
                String vote_avg = movieObject.getString(VOTE_AVG);
                String bgUrl = movieObject.getString(BG_URL);

                movieResults.add(new MovieObj(title, year, "https://image.tmdb.org/t/p/" + "w342" + posterUrl, plot, id, "https://image.tmdb.org/t/p/" + "w780" + bgUrl, vote_avg));
            }

            return movieResults;
        }


        @Override
        protected void onPostExecute (ArrayList < MovieObj > movies) {
            super.onPostExecute(movies);

            if (movies != null) {
                searchAdapter = new GridViewAdapter(context, movies);
                gridView.setAdapter(searchAdapter);
            }

        }



}
