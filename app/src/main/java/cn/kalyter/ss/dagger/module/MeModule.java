package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.contract.MeContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.MePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Module
public class MeModule {
    private MeContract.View mView;
    private Context mContext;

    public MeModule(MeContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    MeContract.Presenter providePresenter(UserService userService,
                                          UserSource userSource,
                                          SplashSource splashSource) {
        return new MePresenter(mView, userSource, userService, splashSource);
    }
}
