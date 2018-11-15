package com.man.moviefavorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.man.moviefavorite.adapter.AdapterFavorite;

import static com.man.moviefavorite.database.DatabaseContract.URI;

public class MainActivity extends AppCompatActivity {

   private RecyclerView rvFavorite;
   private Cursor list;
   private AdapterFavorite adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFavorite = findViewById(R.id.RecFavorite);
        adapter = new AdapterFavorite(list);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setAdapter(adapter);
        new loadData().execute();
    }

    private class loadData extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
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
