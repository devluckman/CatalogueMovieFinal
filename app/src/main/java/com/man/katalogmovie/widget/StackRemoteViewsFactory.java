package com.man.katalogmovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.man.katalogmovie.BuildConfig;
import com.man.katalogmovie.R;
import com.man.katalogmovie.model.Results;

import java.util.concurrent.ExecutionException;

import static com.man.katalogmovie.database.DatabaseContract.URI;

public class StackRemoteViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Results results = getResults(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.IMG185_URL+ results.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d("Widget Load Error","error");
        }
        rv.setImageViewBitmap(R.id.imageView, bmp);


        Bundle bundle = new Bundle();
        bundle.putInt(StackWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        rv.setOnClickFillInIntent(R.id.imageView, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Results getResults (int position){
        if (!cursor.moveToPosition(position)) {
            throw  new IllegalStateException("Failed");
        }
            return new Results(cursor);

    }
}
