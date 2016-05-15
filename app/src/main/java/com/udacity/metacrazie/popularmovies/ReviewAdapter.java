package com.udacity.metacrazie.popularmovies;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pratyusha on 28/04/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>
{

    public ArrayList<ReviewObj> reviewArray;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }

        TextView reviewBody;
        TextView reviewAuthor;
    }

    public ReviewAdapter(ArrayList<ReviewObj> rArrayList, Context c) {
        reviewArray = rArrayList;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.reviewBody = (TextView) v.findViewById(R.id.review_body);
        viewHolder.reviewAuthor = (TextView) v.findViewById(R.id.review_author);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ReviewObj review = reviewArray.get(position);
        holder.reviewBody.setText(review.reviewBody);
        if (!review.reviewAuthor.equals(""))
            holder.reviewAuthor.setText(review.reviewAuthor);
        else
            holder.reviewAuthor.setVisibility(View.GONE);

      /*  holder.reviewBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder reviewDialog = new AlertDialog.Builder(context, R.style.AppTheme_PopupOverlay);
                AlertDialog show = reviewDialog.setMessage(review.getRText())
                        .setTitle(context.getString(R.string.author) + review.getRAuth())
                        .show();
            }
        }); */
    }

    @Override
    public int getItemCount() {
        return reviewArray.size();
    }
}