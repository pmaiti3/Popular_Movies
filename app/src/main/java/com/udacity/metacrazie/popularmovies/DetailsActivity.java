package com.udacity.metacrazie.popularmovies;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pratyusha on 24/03/2016.
 */
public class DetailsActivity extends AppCompatActivity {
    int id;
    String title;
    String plot;
    String date;
    String poster;
    String vote;
    String bg;


    ArrayList<ReviewObj> rList = new ArrayList<>();
    RecyclerView tView;

    ArrayList<TrailerObj> tList = new ArrayList<>();
    RecyclerView rView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        if (savedInstanceState==null)
        {
            Bundle extras= getIntent().getExtras();
            if(extras!=null)
            {
                id= extras.getInt("id");
                title= extras.getString("title", "Title");
                plot= extras.getString("plot", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
                date= extras.getString("release_date", "01-01-1970");
                poster= extras.getString("poster", "https://c1.staticflickr.com/1/186/382004453_f4b2772254.jpg");
                vote= extras.getString("vote_avg", "0.0");
                bg= extras.getString("bg");
                update(title, plot, date, vote, poster, bg);
            }
        }
        else
        {
            id= savedInstanceState.getInt("id");
            title= savedInstanceState.getString("title", "Title");
            plot= savedInstanceState.getString("plot", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
            date= savedInstanceState.getString("release_date", "01-01-1970");
            poster= savedInstanceState.getString("poster","https://c1.staticflickr.com/1/186/382004453_f4b2772254.jpg");
            vote= savedInstanceState.getString("vote_avg", "0.0");
            bg= savedInstanceState.getString("bg");
            update(title, plot, date, vote, poster, bg);
        }


        rView = (RecyclerView) findViewById(R.id.reviews);
        tView= (RecyclerView) findViewById(R.id.trailers);
        tView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rView.setNestedScrollingEnabled(true);
        rView.setHasFixedSize(true);


        new FetchTrailers(this, tView, tList, id).execute("trailers" );
        new FetchReviews(this, rView, rList, id).execute("reviews");


    }


   /* public void showFragment()
    {
        DetailsFragment detailsFragment=DetailsFragment.newInstance();

        FragmentTransaction transact=getSupportFragmentManager().beginTransaction();

        transact.add(R.id.fragment_details, detailsFragment, "lastSMSFragment");

        transact.commit();
    } */



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.share_details) {
            ImageView poster = (ImageView) findViewById(R.id.poster);
            Intent sharePage = new Intent(Intent.ACTION_SEND);
            String url= "https://www.themoviedb.org/movie/"+id;
            sharePage.putExtra(Intent.EXTRA_TEXT, url);
            sharePage.setType("text/plain");
            startActivity(Intent.createChooser(sharePage, "Share link via"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        state.putInt("id", id);
        state.putString("title", title);
        state.putString("plot", plot);
        state.putString("release_date", date);
        state.putString("vote_avg", vote);
        state.putString("poster", poster);
        state.putString("bg", bg);
    }



    public void update(String t, String p, String d, String v, final String post, String back) {


        CollapsingToolbarLayout c= (CollapsingToolbarLayout) findViewById(R.id.toolbar_collapse);
        c.setTitle(t);
        TextView plot = (TextView) findViewById(R.id.mplot);
        TextView date = (TextView) findViewById(R.id.mdate);
        TextView vote = (TextView) findViewById(R.id.mvote);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        ImageView backdrop = (ImageView) findViewById(R.id.bg_img);
        poster.setMinimumHeight(poster.getWidth());
        plot.setText(p);
        date.setText(d);
        vote.setText(v);
        Picasso.with(this)
                .load(post)
                .error(R.drawable.placeholder)
                .into(poster);
        Picasso.with(this)
                .load(back)
                .into(backdrop);
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}


