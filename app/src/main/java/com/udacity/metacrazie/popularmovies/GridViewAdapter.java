package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pratyusha on 24/03/2016.
 */
public class GridViewAdapter extends ArrayAdapter<MovieObj> {

    private Context context;
    private ArrayList<MovieObj> movies = new ArrayList<>();
    public GridViewAdapter(Context context, ArrayList<MovieObj> mMovies) {
        super(context, R.layout.grid_item);
        this.context = context;
        this.movies = mMovies;
    }

    @Override
    public int getCount() {
        if (movies == null) {
            return 0;
        } else return movies.size();
    }

    @Override
    public MovieObj getItem(int position) {
        return movies.get(position);
    }

    @Override
    public int getPosition(MovieObj item) {
        return movies.indexOf(item);
    }

    private static class ViewHolder {
        ImageView posterView;
        TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.posterView = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        MovieObj movie = movies.get(position);
        Picasso.with(context).load(movie.getPosterUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.posterView);
        return convertView;
    }
}