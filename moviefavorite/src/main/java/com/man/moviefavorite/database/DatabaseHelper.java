package com.man.moviefavorite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DatabaseHelper {

    private static String TABLE = MovieColumns.TABLE;
    private Context context;
    private Database database;
    private SQLiteDatabase liteDatabase;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public DatabaseHelper open() throws SQLException {
        database = new Database(context);
        liteDatabase = database.getWritableDatabase();
        return this;
    }
    public void close(){
        database.close();
    }

    public Cursor queryProvider(){
        return liteDatabase.query(
                TABLE,
                null,
                null,
                null,
                null,
                null,
                MovieColumns.MOVIE_ID +" DESC"
        );
    }

    public Cursor queryById(String id){
        return liteDatabase.query(
                TABLE,
                null,
                MovieColumns.MOVIE_ID + " =?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values){
        return liteDatabase.insert(
                TABLE,
                null,
                values
        );
    }

    public int delete(String id){
        return liteDatabase.delete(
                TABLE,
                MovieColumns.MOVIE_ID+ " = ?",
                new String[]{id}
                );
    }

    public int updateProvider(String id, ContentValues values){
        return liteDatabase.update(
                TABLE,
                values,
                MovieColumns.MOVIE_ID + " = ?",
                new String[]{id});
    }

    public int deleteProvider(String id){
        return liteDatabase.delete(TABLE,
                MovieColumns.MOVIE_ID + " = ?",
                new String[]{id});
    }


}
