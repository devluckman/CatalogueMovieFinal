package com.man.katalogmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.man.katalogmovie.R;
import com.man.katalogmovie.adapter.Adapter;
import com.man.katalogmovie.model.ModelRespone;
import com.man.katalogmovie.model.Results;
import com.man.katalogmovie.utils.ApiClient;
import com.man.katalogmovie.utils.Language;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowplayingFragment extends Fragment {

    private Context context;
    private Adapter adapter;
    private RecyclerView RecNowplay;


    private Call<ModelRespone> apiInterface;
    private ApiClient apiClient = new ApiClient();

    List<Results> movieList;

    public NowplayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        context = view.getContext();
        RecNowplay = view.findViewById(R.id.RecNowplaying);

        setView();
        loadData();

        return view;
    }

    public void setView(){
        adapter = new Adapter(getActivity());
        RecNowplay.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecNowplay.setHasFixedSize(true);
    }

    private void loadData(){
        apiInterface = apiClient.getApiInterface().MovieNowPlay(Language.getLanguage());
        apiInterface.enqueue(new Callback<ModelRespone>() {
            @Override
            public void onResponse(Call<ModelRespone> call, Response<ModelRespone> response) {
                if (response.isSuccessful()){
                    movieList = response.body().getResults();
                    adapter.setResult(movieList);
                    RecNowplay.setAdapter(adapter);
                }else {
                    Toast.makeText(context,  R.string.pesan,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRespone> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie", new ArrayList<>(adapter.getList()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!= null){
            ArrayList<Results> results ;
            results = savedInstanceState.getParcelableArrayList("movie");
            adapter.setResult(results);
            RecNowplay.setAdapter(adapter);
        }
    }
}
