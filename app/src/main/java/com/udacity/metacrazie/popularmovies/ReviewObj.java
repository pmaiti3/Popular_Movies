package com.udacity.metacrazie.popularmovies;

/**
 * Created by Pratyusha on 28/04/2016.
 */
public class ReviewObj {

    String reviewAuthor;
    String reviewBody;

    public ReviewObj(String author, String text) {
        this.reviewAuthor = author;
        this.reviewBody = text;
    }

    public String getRAuth() {
        return reviewAuthor;
    }
    public void setrAuth(String author) {
        this.reviewAuthor = author;
    }
    public String getRText() {
        return reviewBody;
    }
    public void setrText(String text) {
        this.reviewBody = text;
    }

}