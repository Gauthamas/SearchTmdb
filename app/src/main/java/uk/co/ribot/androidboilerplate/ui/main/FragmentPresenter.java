package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import javax.inject.Inject;

import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieCollection;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;

/**
 * Created by gauthama on 25/5/17.
 */
public class FragmentPresenter extends BasePresenter<FragmentMvpView> {

    private final DataManager mDataManager;


    @Inject
    public FragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }


    public void searchMovies(String page){
        checkViewAttached();
        mDataManager.continueSearching(page);
    }

    public void makeSenseOfMovies(MovieCollection movieCollection, int page, boolean requested){
        int mpage = Integer.parseInt(movieCollection.page);
        if(mpage==1){
            getMvpView().clearAndAddMovies(movieCollection.result, mpage);
        }
        else{
            if((mpage==(page+1)) && requested){
                getMvpView().updateMovies(movieCollection.result,mpage);
            }
        }

    }

    public void onMovieClicked(List<Movie>movies, int position){
        getMvpView().showMovieTitle(movies.get(position).title);

    }


}
