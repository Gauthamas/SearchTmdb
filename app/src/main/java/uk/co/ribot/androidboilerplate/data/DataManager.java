package uk.co.ribot.androidboilerplate.data;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieCollection;
import uk.co.ribot.androidboilerplate.data.remote.TMDBService;
import uk.co.ribot.androidboilerplate.util.RxEventBus;

@Singleton
public class DataManager {

    private final TMDBService mTMDBService;
    private final RxEventBus mRxEventBus;
    private String mQuery;

    @Inject
    public DataManager(TMDBService TMDBService, RxEventBus rxEventBus) {
        mTMDBService = TMDBService;
        mRxEventBus = rxEventBus;
    }


    public void searchMovies(String query, String page){
        final String posterprefix = "http://image.tmdb.org/t/p/w185";
        mQuery = query;
        mTMDBService.getSearchedMovies(query,"ed75ba81d3a8253393684108406b8e26",page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<MovieCollection>() {
                    @Override
                    public void call(MovieCollection movieCollection) {
                        if(movieCollection.result!=null) {
                            for (Movie m : movieCollection.result) {
                                m.posterURI = posterprefix + m.posterURI;
                            }
                            mRxEventBus.post(movieCollection);
                        }

                    }
                });



    }

    public void continueSearching(String page){
        searchMovies(mQuery, page);
    }


}
