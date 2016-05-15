package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {


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




    public DetailsFragment() {
        // Required empty public constructor

    }


    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
