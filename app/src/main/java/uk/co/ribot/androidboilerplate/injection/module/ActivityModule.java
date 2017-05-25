package uk.co.ribot.androidboilerplate.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.injection.ActivityContext;
import uk.co.ribot.androidboilerplate.injection.PerActivity;
import uk.co.ribot.androidboilerplate.injection.PerFragment;
import uk.co.ribot.androidboilerplate.ui.main.MovieAdapter;
import uk.co.ribot.androidboilerplate.ui.main.MovieGridAdapter;
import uk.co.ribot.androidboilerplate.ui.main.ViewPagerAdapter;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    protected ViewPagerAdapter provideVerticalViewPagerAdapter() {
        return new ViewPagerAdapter(((AppCompatActivity)mActivity).getSupportFragmentManager());
    }

    @Provides
    protected MovieAdapter provideMovieAdapter() {
        return new MovieAdapter(mActivity);
    }

    @Provides
    protected MovieGridAdapter provideGridMovieAdapter() {
        return new MovieGridAdapter(mActivity);
    }
}
