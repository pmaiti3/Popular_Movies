package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
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
 * Created by Pratyusha on 14/05/2016.
 */
public class FetchTrailers extends AsyncTask<String, Void, ArrayList<TrailerObj>> {
    Context context;
    public TrailerAdapter trailerAdapter;
    RecyclerView trailerView;
    SharedPreferences pref;
    ArrayList<TrailerObj> trailerResults;
    int movieId;
    Uri buildUri;

    public FetchTrailers(Context c, RecyclerView v, ArrayList<TrailerObj> trRes, int movieId) {
        super();
        context = c;
        trailerView = v;
        this.trailerResults = trRes;
        this.movieId = movieId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!trailerResults.isEmpty()) {
            trailerResults.clear();
        }
        Log.d("AsyncTask", "Started");
    }
    @Override
    protected ArrayList<TrailerObj> doInBackground(String... strings) {
        if (strings.length == 0) {
            return null;
        }
        HttpURLConnection client = null;
        BufferedReader bufferedReader = null;
        String searchJSONstr = null;
        final String SEARCH_BASE_URL =
                "https://api.themoviedb.org/3/movie/";
        final String API_KEY = context.getString(R.string.api_key);
        final String API_KEY_PARAM = "api_key";

        //build the review URL
        buildUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
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
        } catch (ProtocolException e1) {
            e1.printStackTrace();
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
                Exception e
                )

        {
            e.printStackTrace();
        }

        return null;
    }
    private ArrayList<TrailerObj> getSearchDataFromJson(String searchJSONstr)
            throws Exception {
        final String LIST_NAME = "results";
        final String NAME = "name";
        final String KEY = "key";

        JSONObject searchResult = new JSONObject(searchJSONstr);
        JSONArray trailerArray = searchResult.getJSONArray(LIST_NAME);

        Log.d("trailerArray", trailerArray.toString());

        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject movieObject = trailerArray.getJSONObject(i);
            String name = movieObject.getString(NAME);
            String id = movieObject.getString(KEY);

            trailerResults.add(new TrailerObj(name, id));
        }

        return trailerResults;

    }

    @Override
    protected void onPostExecute(ArrayList<TrailerObj> Trailers) {
        super.onPostExecute(Trailers);

        if (Trailers.isEmpty()) {
            Trailers.add(new TrailerObj("", context.getString(R.string.no_reviews)));
        }
        trailerAdapter = new TrailerAdapter(Trailers, context);
        trailerView.setAdapter(trailerAdapter);
        trailerView.setVisibility(View.VISIBLE);
    }

    }
