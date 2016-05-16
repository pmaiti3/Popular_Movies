package com.udacity.metacrazie.popularmovies;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

 private CoordinatorLayout coordinatorLayout;
    private static final String TAG = "MyActivity";
    GridView moviesGridView;
    public ArrayList<MovieObj> movieResults = new ArrayList<>();
    boolean dualPane;
    public MovieDBHandler mDb;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        dualPane=true;

        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        dualPane=false;

        mDb = new MovieDBHandler(this);

        pref = getSharedPreferences("Prefs", MODE_PRIVATE);



        //nav controls
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        // nav controls end

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = getPreferences(Context.MODE_PRIVATE);
                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                //  If the activity has never started before...
                if (isFirstStart) {
                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, Introduction.class);
                    startActivity(i);
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();
                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);
                    //  Apply changes
                    e.apply();
                }
            }
        });
        // Start the thread
        t.start();



    moviesGridView = (GridView) findViewById(R.id.gridview);
    TextView label = (TextView) findViewById(R.id.label);


    SharedPreferences pref = getSharedPreferences("Prefs", MODE_PRIVATE);
    String s = pref.getString("sort", "popular");
    if (s.equals("popular")) {
        assert label != null;
        label.setText(getString(R.string.popular));
    } else if (s.equals("top_rated")) {
        assert label != null;
        label.setText(getString(R.string.rating));
    } else if (s.equals("upcoming")) {
        assert label != null;
        label.setText(getString(R.string.upcoming));
    } else if (s.equals("now_playing")) {
        assert label != null;
        label.setText(getString(R.string.now_playing));
    }
    else if (s.equals("fav")) {
        assert label != null;
        label.setText(getString(R.string.favourites));
    }
        if(isNetworkAvailable()) {
    GridViewAdapter mAdapter = new GridViewAdapter(this, movieResults);
    moviesGridView.setAdapter(mAdapter);
    populateMovies();
}
        else
        Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        Log.d(TAG,"Network");
    }



    public void onClick(View v)
    {
        int p=moviesGridView.getPositionForView(v);
        MovieObj m= (MovieObj) moviesGridView.getItemAtPosition(p);

        if(dualPane) Log.d(TAG, "Landscape View");
        else
        Log.d(TAG, "Portrait View");
        //Portrait
        if (!dualPane){
        Intent i=new Intent(MainActivity.this, DetailsActivity.class);
        i.putExtra("id", m.id);
        i.putExtra("title", m.title);
        i.putExtra("release_date", m.year);
        i.putExtra("vote_avg", m.vote_avg);
        i.putExtra("plot", m.plot);
        i.putExtra("poster", m.poster);
        i.putExtra("bg", m.bg);
        startActivity(i); }
        //Landscape
        else
        {
            DetailsFragment details = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details);


            try {

                Bundle bundle=new Bundle();
                bundle.putInt("id", m.id);
                bundle.putString("title", m.title);
                bundle.putString("release_date", m.year);
                bundle.putString("vote_avg", m.vote_avg);
                bundle.putString("plot", m.plot);
                bundle.putString("poster", m.poster);
                bundle.putString("bg", m.bg);

                findViewById(R.id.blankText).setVisibility(View.GONE);
                if (isNetworkAvailable()) {

                    DetailsFragment fragment = new DetailsFragment();
                    fragment.setArguments(bundle);
                    // Make new fragment to show this selection.
                    details = DetailsFragment.newInstance(p, bundle);


                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.details, details).commit();
                    // Commit the transaction

                }
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            if(isNetworkAvailable())
            populateMovies();
            else
                Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        SharedPreferences pref= getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        TextView label= (TextView) findViewById(R.id.label);

        if (id == R.id.popular) {
            edit.putString("sort", "popular");
            label.setText(getString(R.string.popular));
            edit.commit();
            if(isNetworkAvailable())
            populateMovies();
            else
                Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        } else if (id == R.id.rating) {
            edit.putString("sort", "top_rated");
            label.setText(getString(R.string.rating));
            edit.commit();
            if(isNetworkAvailable())
            populateMovies();
            else
                Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        }else if (id == R.id.upcoming) {
            edit.putString("sort", "upcoming");
            label.setText(getString(R.string.upcoming));
            edit.commit();
            if(isNetworkAvailable())
            populateMovies();
            else
                Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        }
        else if (id == R.id.now_playing) {
            edit.putString("sort", "now_playing");
            label.setText(getString(R.string.now_playing));
            edit.commit();
            if(isNetworkAvailable())
            populateMovies();
            else
                Snackbar.make(coordinatorLayout, "Please connect to internet and try again", Snackbar.LENGTH_INDEFINITE).show();
        }
        else if (id == R.id.fav) {
            edit.putString("sort", "fav");
            label.setText(getString(R.string.favourites));
            edit.commit();
            populateMovies();
        }
        else if (id == R.id.about) {

            Intent i= new Intent(MainActivity.this, About.class);
            startActivity(i);
        } else if (id == R.id.action_settings) {
            Snackbar.make(coordinatorLayout, "Feature yet to be added", Snackbar.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void populateMovies() {

        moviesGridView.removeAllViewsInLayout();
        String sort = pref.getString("sort", "popular");
        if (sort.equals("fav")) {
            Log.d("Fav", "show favourites");
            movieResults = mDb.getAllMovies();
            GridViewAdapter mAdapter = new GridViewAdapter(this, movieResults);
            moviesGridView.setAdapter(mAdapter);

            if (movieResults.isEmpty()) {
                Snackbar.make(coordinatorLayout, R.string.no_fav, Snackbar.LENGTH_LONG).show();
            }
        } else new FetchSearchResults(this, moviesGridView, movieResults, pref).execute("discover");

    }

    private boolean isNetworkAvailable() {
        boolean isNetworkAvailable = false;

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvailable = true;
        }

        return isNetworkAvailable;

    }}
