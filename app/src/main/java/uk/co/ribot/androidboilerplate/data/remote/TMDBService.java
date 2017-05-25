package uk.co.ribot.androidboilerplate.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.MovieCollection;

public interface TMDBService {

    String ENDPOINT = "http://api.themoviedb.org";

    @GET("/3/search/movie")
    Observable<MovieCollection> getSearchedMovies(@Query("query") String query,
                                          @Query("api_key") String api_key, @Query("page") String page);


    /******** Helper class that sets up a new services *******/
    class Creator {

        public static TMDBService newRibotsService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TMDBService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(TMDBService.class);
        }
    }
}
