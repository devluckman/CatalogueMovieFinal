package com.man.katalogmovie.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.man.katalogmovie.R;
import com.man.katalogmovie.adapter.Adapter;
import com.man.katalogmovie.adapter.AdapterFavorite;

import static com.man.katalogmovie.database.DatabaseContract.URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private Context context;
    private AdapterFavorite adapter;
    private RecyclerView RecFavorite;
    private Cursor list;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = view.getContext();

        //set adapter
        RecFavorite = view.findViewById(R.id.RecFavorite);
        adapter = new AdapterFavorite(list);
        RecFavorite.setLayoutManager(new LinearLayoutManager(context));
        RecFavorite.setAdapter(adapter);

        new loadData().execute();
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        new loadData().execute();
    }

    private class loadData extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return context.getContentResolver().query(
                    URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            list = cursor;
            adapter.replace(list);
        }
    }

}
