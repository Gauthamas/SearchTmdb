package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    Context activityContext;

    @Inject
    public MovieAdapter(Context context) {

        activityContext = context;
        mMovies = new ArrayList<>();
    }

    public void setmMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        com.squareup.picasso.Callback callback = new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageView.getDrawable();
                Palette.from(bitmapDrawable.getBitmap()).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette p) {
                        Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                        if (vibrantSwatch != null) {
                            holder.mCardView.setBackgroundColor(vibrantSwatch.getRgb());
                            holder.titleTextView.setTextColor(vibrantSwatch.getTitleTextColor());
                            holder.descTextView.setTextColor(vibrantSwatch.getBodyTextColor());
                        }

                    }
                });

            }

            @Override
            public void onError() {

            }
        };
        holder.titleTextView.setText(movie.title);
        holder.descTextView.setText(movie.overview);
        Picasso.with(activityContext).load(movie.posterURI).
                into(holder.imageView, callback);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview)
        ImageView imageView;
        @BindView(R.id.text_title) TextView titleTextView;
        @BindView(R.id.text_desc) TextView descTextView;
        @BindView(R.id.card_view)
        CardView mCardView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
