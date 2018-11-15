package com.man.katalogmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.man.katalogmovie.R;
import com.man.katalogmovie.model.Results;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Results> list = new ArrayList<>();
    private Context context;

    public Adapter(Context context) {
        this.context = context;
    }
    public void setResult(List<Results> resultsList){
        this.list = resultsList;
    }

    public List<Results> getList(){
        return list;
    }

    public Adapter() {
    }
    public void replace(List<Results> results){
        list.clear();
        list = results;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindView(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
