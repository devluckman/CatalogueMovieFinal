package com.man.katalogmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.man.katalogmovie.adapter.Adapter;
import com.man.katalogmovie.model.ModelRespone;
import com.man.katalogmovie.model.Results;
import com.man.katalogmovie.utils.ApiClient;
import com.man.katalogmovie.utils.Language;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.RecSearchmovie)
    RecyclerView RecSearchmovie;
    private EditText editText;
    @BindView(R.id.btn_Search)
    Button btnSearch;
    @BindView(R.id.progress)
    ProgressBar progress;


    private Adapter adapter;

    private Call<ModelRespone> apiInterface;
    private ApiClient apiClient = new ApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText = findViewById(R.id.tv_SearchMoview);
        ButterKnife.bind(this);
        adapter = new Adapter();
        RecSearchmovie.setLayoutManager(new LinearLayoutManager(this));
        RecSearchmovie.setAdapter(adapter);
        getSupportActionBar().setTitle("Search Movie");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    private void loadData() {
        String movie = editText.getText().toString();
        apiInterface = apiClient.getApiInterface().SearchMovie(movie, Language.getLanguage());
        apiInterface.enqueue(new Callback<ModelRespone>() {
            @Override
            public void onResponse(Call<ModelRespone> call, Response<ModelRespone> response) {
                if (response.isSuccessful()) {
                    adapter.replace(response.body().getResults());
                } else {
                    Toast.makeText(SearchActivity.this, R.string.pesan, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRespone> call, Throwable t) {

            }
        });
    }


}
