package uk.co.ribot.androidboilerplate.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieCollection {

    @SerializedName("results")
    public List<Movie> result;

    @SerializedName("page")
    public String page;

    @SerializedName("total_results")
    public String total_results;

    @SerializedName("total_pages")
    public String total_pages;


}
