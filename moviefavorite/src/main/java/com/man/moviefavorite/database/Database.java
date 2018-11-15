package com.man.moviefavorite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    public static String DATABASE_NAME = "db";

    public static String TABLE_FAVORITE = MovieColumns.TABLE;

    public static String COL_ID = MovieColumns.MOVIE_ID;
    public static String COL_TITLE = MovieColumns.TITLE;
    public static String COL_DESC = MovieColumns.DESKRIPSI;
    public static String COL_RELEASE = MovieColumns.REALEASE;
    public static String COL_POSTER = MovieColumns.POSTER;
    public static String COL_VOTE = MovieColumns.VOTE;



    public static String CREATE_TABLE_FAVORITE = "create table "+TABLE_FAVORITE+" ("
            + COL_ID + " integer primary key autoincrement, "
            + COL_TITLE + " text not null, "
            + COL_DESC + " text not null, "
            + COL_RELEASE + " text not null, "
            + COL_POSTER + " text not null, "
            + COL_VOTE + " text not null ); ";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_FAVORITE);
        onCreate(db);
    }
}
