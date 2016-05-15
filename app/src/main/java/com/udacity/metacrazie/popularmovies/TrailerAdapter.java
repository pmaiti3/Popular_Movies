package com.udacity.metacrazie.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Pratyusha on 03/05/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    public ArrayList<TrailerObj> trailerArray;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }

        TextView trailerTitle;
        ImageView trailerIcon;
    }

    public TrailerAdapter(ArrayList<TrailerObj> tArrayList, Context c)
    {
        trailerArray=tArrayList;
        context=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.trailerTitle = (TextView) v.findViewById(R.id.trailer_title);
        viewHolder.trailerIcon = (ImageView) v.findViewById(R.id.trailer_image);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final TrailerObj trailer=trailerArray.get(position);
        holder.trailerTitle.setText(trailer.gettTitle());
        Glide.with(context).load("http://img.youtube.com/vi/" + trailer.gettID() + "/0.jpg")
                .into(holder.trailerIcon);
        holder.trailerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrailerObj t = trailerArray.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be/" + t.gettID()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerArray.size();
    }
}
