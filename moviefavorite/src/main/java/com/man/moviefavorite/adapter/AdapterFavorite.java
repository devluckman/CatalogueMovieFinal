package com.man.moviefavorite.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.man.moviefavorite.BuildConfig;
import com.man.moviefavorite.DetailActivity;
import com.man.moviefavorite.R;
import com.man.moviefavorite.model.Results;

import static com.man.moviefavorite.database.DatabaseContract.URI;

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

    private Results getResult(int postion) {
        if (!cursor.moveToPosition(postion)) {
            throw new IllegalStateException("Failed!");
        }
        return new Results(cursor);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDesc;
        private ImageView imgPoster;
        private CardView cvMain;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.list_title);
            tvDesc = itemView.findViewById(R.id.list_desc);
            imgPoster = itemView.findViewById(R.id.img_movie);
            cvMain = itemView.findViewById(R.id.cv_main);
        }

        public void bind(final Results results) {
            String url = BuildConfig.IMG185_URL+results.getPosterPath();
            tvTitle.setText(results.getTitle());
            tvDesc.setText(results.getOverview());
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imgPoster);
            cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.setData(Uri.parse(URI + "/" +results.getId()));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
