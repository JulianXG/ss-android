package cn.kalyter.ss.presenter;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.MeContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class MePresenter implements MeContract.Presenter {
    private MeContract.View mView;
    private UserSource mUserSource;
    private UserService mUserService;
    private SplashSource mSplashSource;

    public MePresenter(MeContract.View view,
                       UserSource userSource,
                       UserService userService,
                       SplashSource splashSource) {
        mView = view;
        mUserSource = userSource;
        mUserService = userService;
        mSplashSource = splashSource;
    }

    @Override
    public void start() {
        loadUser();
    }

    @Override
    public void loadUser() {
        User user = mUserSource.getUser();
        if (user.getRoleId() == Config.ROLE_ADMIN) {
            mView.showAdmin();
        } else {
            mView.showNormalUser();
        }
        mView.showUser(user);
    }

    @Override
    public void logout() {
        mSplashSource.updateIsLogin(false);
        mView.showLogin();
    }
}
