package com.man.katalogmovie.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.man.katalogmovie.BuildConfig;
import com.man.katalogmovie.DetailActivity;
import com.man.katalogmovie.R;
import com.man.katalogmovie.model.Results;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.MyHolder> {

    private Cursor cursor;

    public AdapterFavorite(Cursor cursor) {
        replace(cursor);
    }

    public void replace(Cursor items) {
        cursor = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.bind(getResult(i));
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private Results getResult(int postion){
        if (!cursor.moveToPosition(postion)){
            throw  new IllegalStateException("Failed!");
        }
        return new Results(cursor);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_movie)
        ImageView imgMovie;
        @BindView(R.id.list_title)
        TextView listTitle;
        @BindView(R.id.list_desc)
        TextView listDesc;
        @BindView(R.id.cv_main)
        CardView cvMain;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Results results){
            String url = BuildConfig.IMG185_URL+results.getPosterPath();
            listTitle.setText(results.getTitle());
            listDesc.setText(results.getOverview());
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
}
