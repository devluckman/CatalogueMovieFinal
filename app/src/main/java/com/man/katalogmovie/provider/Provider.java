package com.man.katalogmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.man.katalogmovie.database.DatabaseContract;
import com.man.katalogmovie.database.DatabaseHelper;

import java.sql.SQLException;

import static com.man.katalogmovie.database.DatabaseContract.URI;

public class Provider extends ContentProvider {
    private static final int FAVORITE = 100;
    private static final int FAVORITE_ID = 101;
    private DatabaseHelper databaseHelper;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(DatabaseContract.AUTHORITY,
                MovieColumns.TABLE, FAVORITE);

        uriMatcher.addURI(DatabaseContract.AUTHORITY,
                MovieColumns.TABLE+ "/#",
                FAVORITE_ID);
    }


    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        try {
            databaseHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri,String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                cursor = databaseHelper.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = databaseHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor=null;
                break;
        }
        return cursor;
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri,
                      ContentValues values) {
        long added;
        switch (uriMatcher.match(uri)){
            case FAVORITE:
                added = databaseHelper.insertProvider(values);
                break;
            default:
                added=0;
                break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(URI + "/" +added);
    }

    @Override
    public int delete(Uri uri,
                      String selection,
                      String[] selectionArgs) {
        int deleted;
        switch (uriMatcher.match(uri)){
            case FAVORITE_ID:
                deleted = databaseHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted= 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        int updated;
        switch (uriMatcher.match(uri)){
            case FAVORITE_ID:
                updated = databaseHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
