package cn.kalyter.ss.presenter;

import cn.kalyter.ss.contract.ProfileContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private UserService mUserService;
    private UserSource mUserSource;

    public ProfilePresenter(ProfileContract.View view,
                            UserService userService,
                            UserSource userSource) {
        mView = view;
        mUserService = userService;
        mUserSource = userSource;
    }

    @Override
    public void start() {
        loadUser();
    }

    @Override
    public void loadUser() {
        User user = mUserSource.getUser();
        mView.showUser(user);
    }
}
