package com.man.katalogmovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelRespone {
    @SerializedName("results")
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public ModelRespone(List<Results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ModelRespone{" +
                "results=" + results +
                '}';
    }
}
