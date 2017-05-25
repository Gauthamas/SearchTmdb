package uk.co.ribot.androidboilerplate.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;
import uk.co.ribot.androidboilerplate.data.model.MovieCollection;
import uk.co.ribot.androidboilerplate.util.RxEventBus;

/**
 * Created by gauthama on 23/5/17.
 */

public class GridFragment extends Fragment implements FragmentMvpView{

    private int previousTotal = 0;
    private boolean loading = true;
    private boolean requested = true;
    private int visibleThreshold = 15;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int scrollvalue = 0;
    private int mPage = 1;
    private List<Movie> mMovies;

    @Inject
    FragmentPresenter mFragmentPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    RxEventBus rxEventBus;

    @Inject
    MovieGridAdapter movieAdapter;

    @Inject
    public GridFragment(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_grid, container, false);
        mMovies = new ArrayList<Movie>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(movieAdapter);
        mRecyclerView.setHasFixedSize(true);

        mFragmentPresenter.attachView(this);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mFragmentPresenter.onMovieClicked(mMovies, position);
                    }
                })
        );

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollvalue += dy;

                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();


                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    requested = true;
                    mFragmentPresenter.searchMovies(Integer.toString(mPage+1));

                    // Do something

                    loading = true;

                }

            }


        });


        rxEventBus.filteredObservable(MovieCollection.class).subscribe(new Action1<MovieCollection>() {
            @Override
            public void call(MovieCollection movieCollection) {
                mFragmentPresenter.makeSenseOfMovies(movieCollection, mPage, requested);
            }
        });

    }

    @Override
    public void updateMovies(List<Movie> movies, int page) {

        mMovies.addAll(movies);
        movieAdapter.setmMovies(mMovies);
        movieAdapter.notifyDataSetChanged();
        mPage = page;
        requested = false;

    }

    @Override
    public void clearAndAddMovies(List<Movie> movies, int page) {
        mMovies.clear();
        mMovies.addAll(movies);
        movieAdapter.setmMovies(mMovies);
        movieAdapter.notifyDataSetChanged();
        mPage = page;
        requested =false;
        loading = true;
        scrollvalue =0;
        previousTotal = 0;
        mRecyclerView.scrollTo(0,scrollvalue);
    }

    @Override
    public void showMovieTitle(String title) {
        Toast.makeText(getActivity(), title,
                Toast.LENGTH_LONG).show();
    }
}
