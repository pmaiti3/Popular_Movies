package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    private static final String TAG = "MyActivity";
    static int id;
   static String title;
   static String plot;
  static   String date;
    static String poster;
    static String vote;
    static String bg;
static Bundle extras;
    CoordinatorLayout coordinatorLayout;
    MovieDBHandler mHelper;
    ArrayList<ReviewObj> rList = new ArrayList<>();
    RecyclerView tView;

    ArrayList<TrailerObj> tList = new ArrayList<>();
    RecyclerView rView;


    public DetailsFragment() {
        // Required empty public constructor

    }

    public static DetailsFragment newInstance(int index, Bundle bundle) {
        DetailsFragment fragment= new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);

        extras=bundle;
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*Bundle extras = this.getArguments();*/
        if(extras!=null) {
            id= extras.getInt("id");
            title= extras.getString("title", "Title");
            plot=extras.getString("plot", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
            date= extras.getString("release_date", "01-01-1970");
            poster= extras.getString("poster", "https://c1.staticflickr.com/1/186/382004453_f4b2772254.jpg");
            vote= extras.getString("vote_avg", "0.0");
            bg= extras.getString("bg");
            Log.d(TAG, "Transfer conducted");
            update(title, plot, date, vote, poster, bg);
        }
        }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.share_details) {
            Intent sharePage = new Intent(Intent.ACTION_SEND);
            String url= "https://www.themoviedb.org/movie/"+id;
            sharePage.putExtra(Intent.EXTRA_TEXT, url);
            sharePage.setType("text/plain");
            startActivity(Intent.createChooser(sharePage, "Share link via"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar= (Toolbar) getActivity().findViewById(R.id.toolbar_detail);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.detailsCoordinatorLayout);
     /*   setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        mHelper = new MovieDBHandler(getActivity());


        if (savedInstanceState==null)
        {
            if(extras!=null)
            {
                id= extras.getInt("id");
                title= extras.getString("title", "Title");
                plot= extras.getString("plot", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
                date= extras.getString("release_date", "01-01-1970");
                poster= extras.getString("poster", "https://c1.staticflickr.com/1/186/382004453_f4b2772254.jpg");
                vote= extras.getString("vote_avg", "0.0");
                bg= extras.getString("bg");
                /*update(title, plot, date, vote, poster, bg);*/
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
           /* update(title, plot, date, vote, poster, bg); */
        }


        CollapsingToolbarLayout c= (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_collapse);
        c.setTitle(title);
        TextView plotText = (TextView) getActivity().findViewById(R.id.mplot);
        Log.d(TAG, "plot set");
        TextView dateText = (TextView) getActivity().findViewById(R.id.mdate);
        Log.d(TAG, "date set");
        TextView voteText = (TextView) getActivity().findViewById(R.id.mvote);
        Log.d(TAG, "vote set");
        ImageView posterImage = (ImageView) getActivity().findViewById(R.id.poster);
        Log.d(TAG, "poster set");
        ImageView backdrop = (ImageView) getActivity().findViewById(R.id.bg_img);
        Log.d(TAG, "backdrop set");

        posterImage.setMinimumHeight(posterImage.getWidth());

        plotText.setText( plot);

        dateText.setText(date);

        voteText.setText(vote);
        Picasso.with(getActivity())
                .load(poster)
                .error(R.drawable.placeholder)
                .into(posterImage);
        Picasso.with(getActivity())
                .load(bg)
                .into(backdrop);


        rView = (RecyclerView) getActivity().findViewById(R.id.reviews);
        tView= (RecyclerView) getActivity().findViewById(R.id.trailers);
        tView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rView.setNestedScrollingEnabled(true);
        rView.setHasFixedSize(true);

        Log.d(TAG, "Fetch  trailers");
        new FetchTrailers(getActivity(), tView, tList, id).execute("trailers" );
        Log.d(TAG, "Fetch review");
        new FetchReviews(getActivity(), rView, rList, id).execute("reviews");
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnFabClick(v);
                }
            });

    }


    public void OnFabClick(View v) {
        if (mHelper.isPresent(id)) {
            mHelper.deleteMovie(id);
            ((FloatingActionButton) v).setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_18dp));
            Snackbar.make(coordinatorLayout, "Removed", Snackbar.LENGTH_SHORT).show();
        } else {
            mHelper.addMovie(id, title, plot, date, vote, poster, bg);
            ((FloatingActionButton) v).setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
            Snackbar.make(coordinatorLayout, "Added", Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle state)
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


       /* CollapsingToolbarLayout c= (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_collapse);
        c.setTitle(t);
        TextView plot = (TextView) getActivity().findViewById(R.id.mplot);
      Log.d(TAG, "plot set");
        TextView date = (TextView) getActivity().findViewById(R.id.mdate);
      Log.d(TAG, "date set");
        TextView vote = (TextView) getActivity().findViewById(R.id.mvote);
      Log.d(TAG, "vote set");

        ImageView poster = (ImageView) getActivity().findViewById(R.id.poster);
      Log.d(TAG, "poster set");
        ImageView backdrop = (ImageView) getActivity().findViewById(R.id.bg_img);
      Log.d(TAG, "backdrop set");
      assert poster != null;
      poster.setMinimumHeight(poster.getWidth());
      assert plot != null;
      plot.setText(p);
      assert date != null;
      date.setText(d);
      assert vote != null;
      vote.setText(v);
        Picasso.with(getActivity())
                .load(post)
                .error(R.drawable.placeholder)
                .into(poster);
        Picasso.with(getActivity())
                .load(back)
                .into(backdrop);  */
    }

    }

