package com.man.katalogmovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.man.katalogmovie.provider.MovieColumns.TITLE;
import static com.man.katalogmovie.provider.MovieColumns.DESKRIPSI;
import static com.man.katalogmovie.provider.MovieColumns.POSTER;
import static com.man.katalogmovie.provider.MovieColumns.REALEASE;
import static com.man.katalogmovie.provider.MovieColumns.VOTE;
import static com.man.katalogmovie.database.DatabaseContract.getColumnString;
import static com.man.katalogmovie.database.DatabaseContract.getColumnInt;
import static com.man.katalogmovie.database.DatabaseContract.getColumnDouble;


public class Results implements Parcelable {

    public Results() {
    }

    @SerializedName("id")
    private int id;

    @SerializedName("overview")
    private String overview;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    protected Results(Parcel in) {
        id = in.readInt();
        overview = in.readString();
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Results(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.overview = getColumnString(cursor, DESKRIPSI);
        this.title = getColumnString(cursor, TITLE);
        this.posterPath = getColumnString(cursor, POSTER);
        this.releaseDate = getColumnString(cursor, REALEASE);
        this.voteAverage = getColumnDouble(cursor, VOTE);
    }

    @Override
    public String toString() {
        return "Results{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", voteAverage=" + voteAverage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }
}
