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
        return poster;
    }

    public void setPosterUrl(String posterUrl) {
        this.poster = posterUrl;
    }
    public String getBackdropUrl() {
        return poster;
    }

    public void setBackdropUrl(String bgUrl) {
        this.bg = bgUrl;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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
    String poster;
    String plot;
    int id;
    String bg;
    String vote_avg;
    String overview;

    public MovieObj(String t, String y, String p, String mPlot, int id, String bg, String vote) {
        this.title = t;
        this.plot = mPlot;
        this.year = y;
        this.poster = p;
        this.id=id;
        this.bg=bg;
        this.vote_avg=vote;
    }
}