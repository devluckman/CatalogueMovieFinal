package com.man.katalogmovie.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.man.katalogmovie.BuildConfig;
import com.man.katalogmovie.DetailActivity;
import com.man.katalogmovie.R;
import com.man.katalogmovie.model.Results;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.img_movie)
    ImageView imgMovie;
    @BindView(R.id.list_title)
    TextView listTitle;
    @BindView(R.id.list_desc)
    TextView listDesc;
    @BindView(R.id.cv_main)
    CardView cvMain;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bindView(final Results results) {
        listTitle.setText(results.getTitle());
        listDesc.setText(results.getOverview());
        String url = BuildConfig.IMG185_URL+results.getPosterPath();
        Glide.with(itemView.getContext())
                .load(url)
                .into(imgMovie);
        cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.KEY, new Gson().toJson(results));
                itemView.getContext().startActivity(intent);

            }
        });

    }
}
