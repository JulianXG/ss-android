package cn.kalyter.ss.dagger.module;

import cn.kalyter.ss.contract.ChangePasswordContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.ChangePasswordPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Module
public class ChangePasswordModule {
    private ChangePasswordContract.View mView;

    public ChangePasswordModule(ChangePasswordContract.View view) {
        mView = view;
    }

    @Provides
    ChangePasswordContract.Presenter providePresenter(UserService userService,
                                                      SplashSource splashSource,
                                                      UserSource userSource) {
        return new ChangePasswordPresenter(
                mView, splashSource, userService, userSource);
    }
}
