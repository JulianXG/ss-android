package cn.kalyter.ss.presenter;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.SettingsContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.LoginUser;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private SettingsContract.View mView;
    private UserSource mUserSource;
    private UserService mUserService;

    public SettingsPresenter(SettingsContract.View view,
                             UserService userService,
                             UserSource userSource) {
        mView = view;
        mUserService = userService;
        mUserSource = userSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void validateOriginPassword(String password) {
        mView.showValidating();
        User user = mUserSource.getUser();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(user.getUsername());
        loginUser.setPassword(password);
        mUserService.login(loginUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showValidateOriginError();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        if (userResponse.getCode() == Config.OK) {
                            mView.showChangePassword();
                        } else {
                            mView.showValidateOriginError();
                        }
                    }
                });
    }

    @Override
    public void changePassword(String newPassword) {

    }
}
