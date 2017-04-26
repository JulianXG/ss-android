package cn.kalyter.ss.dagger.module;

import cn.kalyter.ss.contract.ProfileContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.ProfilePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

@Module
public class ProfileModule {
    private ProfileContract.View mView;

    public ProfileModule(ProfileContract.View view) {
        mView = view;
    }

    @Provides
    ProfileContract.Presenter providePresenter(UserService userService,
                                               UserSource userSource) {
        return new ProfilePresenter(mView, userService, userSource);
    }
}
