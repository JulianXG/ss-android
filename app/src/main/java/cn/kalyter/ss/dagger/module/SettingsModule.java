package cn.kalyter.ss.dagger.module;

import cn.kalyter.ss.contract.SettingsContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.SettingsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Module
public class SettingsModule {
    private SettingsContract.View mView;

    public SettingsModule(SettingsContract.View view) {
        mView = view;
    }

    @Provides
    SettingsContract.Presenter providePresenter(UserService userService,
                                                UserSource userSource) {
        return new SettingsPresenter(mView, userService, userSource);
    }
}
