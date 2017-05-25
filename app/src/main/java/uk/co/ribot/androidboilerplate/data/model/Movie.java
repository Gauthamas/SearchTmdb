package uk.co.ribot.androidboilerplate.data.model;

import com.google.gson.annotations.SerializedName;


public class Movie{
    @SerializedName("poster_path")
    public String posterURI;

    @SerializedName("title")
    public String title;

    @SerializedName("overview")
    public String overview;

}