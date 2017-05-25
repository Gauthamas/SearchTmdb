package uk.co.ribot.androidboilerplate.ui.main;

/**
 * Created by gauthama on 25/5/17.
 */

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface FragmentMvpView extends MvpView {

    void updateMovies(List<Movie> movies, int page);
    void clearAndAddMovies(List<Movie> movies, int page);
    void showMovieTitle(String title);


}
