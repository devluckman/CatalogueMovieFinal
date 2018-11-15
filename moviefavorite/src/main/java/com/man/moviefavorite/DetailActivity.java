package com.man.moviefavorite;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.man.moviefavorite.model.Results;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvDesc, tvdate, tvVote;
    private ImageView imgPoster;
    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.selecttxtJudul);
        tvDesc = findViewById(R.id.selecttxtDeskripsi);
        tvdate = findViewById(R.id.selecttxtTahun);
        tvVote = findViewById(R.id.selecttxtVote);
        imgPoster = findViewById(R.id.imgselectMovie);

        loadData();
        if (results != null){
            tvTitle.setText(results.getTitle());
            tvDesc.setText(results.getOverview());
            tvdate.setText(String.format(getString(R.string.date)
                    ,String.valueOf(results.getReleaseDate())));
            tvVote.setText(String.format(getString(R.string.movie_rate)
                    ,String.valueOf(results.getVoteAverage())));
            String url = BuildConfig.IMG185_URL+results.getPosterPath();
            Glide.with(this)
                    .load(url)
                    .into(imgPoster);

        }
    }


    private void loadData(){
        Uri uri = getIntent().getData();
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );
        if (cursor != null){
            if (cursor.moveToFirst()) results = new Results(cursor);
            cursor.close();
        }
    }

}
