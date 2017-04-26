/**
 * Created by Julian on 2016/9/13.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import cn.kalyter.ss.contract.MainFrameContract;
import cn.kalyter.ss.presenter.MainFramePresenter;
import cn.kalyter.ss.view.MainFrameActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class MainFrameModule {
    private MainFrameContract.View mView;
    private MainFrameActivity mMainFrameActivity;

    public MainFrameModule(MainFrameContract.View view,
                           MainFrameActivity mainFrameActivity) {
        mView = view;
        mMainFrameActivity = mainFrameActivity;
    }

    @Provides
    MainFrameContract.View provideView() {
        return mView;
    }

    @Provides
    MainFrameContract.Presenter provideMainPresenter(MainFrameContract.View view) {
        return new MainFramePresenter(view, mMainFrameActivity);
    }

    @Provides
    FragmentManager provideManager() {
        return mMainFrameActivity.getSupportFragmentManager();
    }
}
