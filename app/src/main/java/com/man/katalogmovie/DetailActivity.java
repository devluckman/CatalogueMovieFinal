package com.man.katalogmovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.man.katalogmovie.database.DatabaseContract;
import com.man.katalogmovie.database.DatabaseHelper;
import com.man.katalogmovie.model.Results;
import com.man.katalogmovie.provider.MovieColumns;
import com.man.katalogmovie.utils.ApiClient;
import com.man.katalogmovie.utils.Language;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.man.katalogmovie.database.DatabaseContract.URI;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY = "key_movie";

    @BindView(R.id.imgselectMovie)
    ImageView imgselectMovie;
    @BindView(R.id.selecttxtJudul)
    TextView selecttxtJudul;
    @BindView(R.id.fav)
    ImageView fav;
    @BindView(R.id.selecttxtDeskripsi)
    TextView selecttxtDeskripsi;
    @BindView(R.id.selecttxtTahun)
    TextView selecttxtTahun;
    @BindView(R.id.selecttxtVote)
    TextView selecttxtVote;

    private Call<Results> apiInterface;
    private ApiClient apiClient = new ApiClient();
    private Gson gson = new Gson();

    private Results results;
    private DatabaseHelper databaseHelper;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

       String extra = getIntent().getStringExtra(KEY);
       results = gson.fromJson(extra, Results.class);
       loadData();

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) FavoriteDelete();
                else FavoriteSave();
                isFavorite = !isFavorite;
                setIsFavorite();
            }
        });
    }

    private void setIsFavorite(){
        if (isFavorite) fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        else fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

   private void loadData(){
        loadDataSql();
        loadDataApi(String.valueOf(results.getId()));
        selecttxtJudul.setText(results.getTitle());
        selecttxtDeskripsi.setText(results.getOverview());
        selecttxtTahun.setText(String.format(getString(R.string.date)
                ,String.valueOf(results.getReleaseDate())));
        selecttxtVote.setText(String.format(getString(R.string.movie_rate)
                ,String.valueOf(results.getVoteAverage())));
        String url = BuildConfig.IMG185_URL+results.getPosterPath();
        Glide.with(this)
                .load(url)
                .into(imgselectMovie);

    }

    private void loadDataSql() {
        databaseHelper = new DatabaseHelper(this);
        try {
            databaseHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = getContentResolver().query(
                Uri.parse(DatabaseContract.URI+ "/"+ results.getId()),
                null,
                null,
                null,
                null
        );
        if (cursor != null){
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setIsFavorite();
    }

    private void loadDataApi(String movie){
        apiInterface = apiClient.getApiInterface().getDetailMovie(movie, Language.getLanguage());
        apiInterface.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()){
                    Results item = response.body();
                    selecttxtJudul.setText(item.getTitle());
                    selecttxtDeskripsi.setText(item.getOverview());
                    selecttxtTahun.setText(String.format(getString(R.string.date)
                            ,String.valueOf(results.getReleaseDate())));
                    selecttxtVote.setText(String.format(getString(R.string.movie_rate)
                            ,String.valueOf(results.getVoteAverage())));
                }else {
                    Toast.makeText(DetailActivity.this, R.string.pesan,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });
    }

    private void FavoriteSave(){
        ContentValues values = new ContentValues();
        values.put(MovieColumns.MOVIE_ID, results.getId());
        values.put(MovieColumns.TITLE, results.getTitle());
        values.put(MovieColumns.DESKRIPSI, results.getOverview());
        values.put(MovieColumns.REALEASE, results.getReleaseDate());
        values.put(MovieColumns.POSTER, results.getPosterPath());
        values.put(MovieColumns.VOTE, results.getVoteAverage());
        getContentResolver().insert(URI, values);
        Toast.makeText(this, R.string.pesan_like, Toast.LENGTH_SHORT).show();
    }

    private void FavoriteDelete(){
        getContentResolver().delete(
                Uri.parse(DatabaseContract.URI+"/"+results.getId()),
                null,
                null
        );
        Toast.makeText(this, R.string.favorite_delete, Toast.LENGTH_SHORT).show();
    }

}
