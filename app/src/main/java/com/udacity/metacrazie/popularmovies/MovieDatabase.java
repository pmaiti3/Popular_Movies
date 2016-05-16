package com.udacity.metacrazie.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by Pratyusha on 16/05/2016.
 */
public class MovieDatabase {

    MovieDatabase()
    {
        //default constructor
    }

    //abstract class to hold movie components
    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_VOTE = "vote";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_BACKDROP = "bg";
}

}
