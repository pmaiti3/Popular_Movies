package com.udacity.metacrazie.popularmovies;

/**
 * Created by Pratyusha on 24/03/2016.
 */
public class MovieObj {
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }


    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public String getBackdropUrl() {
        return posterUrl;
    }

    public void setBackdropUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }


    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getRating() { return vote_avg; }

    public void setRating(String vote_avg) { this.vote_avg = vote_avg; }

    String title;
    String year;
    String posterUrl;
    String plot;
    int id;
    String bgUrl;
    String vote_avg;

    public MovieObj(String t, String y, String p, String mPlot, int id, String bg, String vote) {
        this.title = t;
        this.plot = mPlot;
        this.year = y;
        this.posterUrl = p;
        this.id=id;
        this.bgUrl=bg;
        this.vote_avg=vote;
    }
}