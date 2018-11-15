package com.man.katalogmovie.utils;

import com.man.katalogmovie.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiInterface apiInterface;

    public ApiClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request req = chain.request();
                        HttpUrl httpUrl = req.url()
                                .newBuilder()
                                .addQueryParameter("api_key",BuildConfig.API_KEY)
                                .build();

                        req = req.newBuilder()
                                .url(httpUrl)
                                .build();

                        return chain.proceed(req);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }
    public ApiInterface getApiInterface(){return apiInterface;}
}
