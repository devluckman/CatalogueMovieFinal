package com.man.katalogmovie.utils;

import com.man.katalogmovie.BuildConfig;
import com.man.katalogmovie.model.ModelRespone;
import com.man.katalogmovie.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("search/movie")
    Call<ModelRespone> SearchMovie(@Query("query") String query, @Query("language") String language);

    @GET ("movie/now_playing")
    Call<ModelRespone> MovieNowPlay(@Query("language") String language);

    @GET ("movie/upcoming")
    Call<ModelRespone> MovieUpcoming(@Query("language") String language);

    @GET("movie/{movie_id}")
    Call<Results> getDetailMovie(@Path("movie_id") String movie_id, @Query("language") String language);

}
