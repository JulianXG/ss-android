package cn.kalyter.ss.presenter;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.ChangePasswordContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    private ChangePasswordContract.View mView;
    private SplashSource mSplashSource;
    private UserService mUserService;
    private UserSource mUserSource;

    public ChangePasswordPresenter(ChangePasswordContract.View view,
                                   SplashSource splashSource,
                                   UserService userService,
                                   UserSource userSource) {
        mView = view;
        mSplashSource = splashSource;
        mUserService = userService;
        mUserSource = userSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void changePassword(String newPassword) {
        mView.showChanging();
        User user = mUserSource.getUser();
        user.setPassword(newPassword);
        mUserService.changePassword(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showChangeError();
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.OK) {
                            mView.showChangeSuccess();
                            mView.showLogin();
                        } else {
                            mView.showChangeError();
                        }
                    }
                });
    }
}
