package com.udacity.metacrazie.popularmovies;

import java.io.Serializable;

/**
 * Created by Pratyusha on 14/05/2016.
 */
public class TrailerObj implements Serializable {

    String trailerTitle;
    String trailerID;

    public TrailerObj(String title, String ID) {
        this.trailerTitle = title;
        this.trailerID = ID;
    }

    public String gettTitle() {
        return trailerTitle;
    }
    public void settTitle(String title) {
        this.trailerTitle = title;
    }
    public String gettID() {
        return trailerID;
    }
    public void settID(String id) {
        this.trailerID = id;
    }

}
