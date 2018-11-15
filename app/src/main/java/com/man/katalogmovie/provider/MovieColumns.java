package com.man.katalogmovie.provider;

import android.provider.BaseColumns;

import com.man.katalogmovie.database.Database;

public class MovieColumns implements BaseColumns {
    public static String TABLE = "favorite";
    public static String MOVIE_ID = "_id";
    public static String TITLE = "title";
    public static String DESKRIPSI = "overview";
    public static String REALEASE = "release_date";
    public static String POSTER = "poster";
    public static String VOTE = "vote";


}
