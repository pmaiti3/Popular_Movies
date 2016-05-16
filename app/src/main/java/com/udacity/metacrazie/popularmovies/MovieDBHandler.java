package com.udacity.metacrazie.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.udacity.metacrazie.popularmovies.MovieDatabase.*;

/**
 * Created by Pratyusha on 16/05/2016.
 */
public class MovieDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Movie.Database";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

    public MovieDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieDatabase.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                        MovieDatabase.MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                        MovieDatabase.MovieEntry.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                        MovieDatabase.MovieEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
                        MovieDatabase.MovieEntry.COLUMN_NAME_VOTE + TEXT_TYPE + COMMA_SEP +
                        MovieDatabase.MovieEntry.COLUMN_NAME_POSTER + TEXT_TYPE + COMMA_SEP +
                        MovieDatabase.MovieEntry.COLUMN_NAME_BACKDROP + TEXT_TYPE + " );";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addMovie( int id, String title, String plot, String date, String vote, String poster, String bg) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieDatabase.MovieEntry._ID, id);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_TITLE, title);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_OVERVIEW, plot);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_YEAR, date);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_VOTE, vote);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_POSTER, poster);
        values.put(MovieDatabase.MovieEntry.COLUMN_NAME_BACKDROP, bg);

        db.insert(MovieEntry.TABLE_NAME, null, values);
        Log.d("DB", "Added");
        db.close();
    }


    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieEntry.TABLE_NAME, MovieEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
        Log.d("DB", "Deleted");
        db.close();
    }


    public ArrayList<MovieObj> getAllMovies() {
        ArrayList<MovieObj> mList = new ArrayList<MovieObj>();
        String selectQuery = "SELECT  * FROM " + MovieEntry.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("MOVIEDB", "Send all movies");
        if (cursor.moveToFirst()) {
            do {
                MovieObj movieObj = new MovieObj( "", "", "", "",0, "", "");
                movieObj.setId(Integer.parseInt(cursor.getString(0)));
                movieObj.setTitle(cursor.getString(1));
                movieObj.setOverview(cursor.getString(2));
                movieObj.setYear(cursor.getString(3));
                movieObj.setRating(cursor.getString(4));
                movieObj.setPosterUrl(cursor.getString(5));
                movieObj.setBackdropUrl(cursor.getString(6));

                mList.add(movieObj);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mList;
    }


    public Boolean isPresent(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        //wrong output CHECK
Log.d("DB", Integer.toString(id));
        Cursor cursor = db.query(MovieDatabase.MovieEntry.TABLE_NAME, new String[]{MovieDatabase.MovieEntry._ID}, MovieDatabase.MovieEntry._ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }


    }

}