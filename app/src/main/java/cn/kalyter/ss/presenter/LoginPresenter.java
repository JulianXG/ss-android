/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.presenter;

import android.util.Log;

import cn.kalyter.ss.contract.LoginContract;
import cn.kalyter.ss.data.local.LoginSource;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.LoginUser;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginContract.View mView;
    private UserService mUserService;
    private LoginSource mLoginSource;
    private SplashSource mSplashSource;

    public LoginPresenter(LoginContract.View view,
                          UserService userService,
                          LoginSource loginSource,
                          SplashSource splashSource) {
        mView = view;
        mUserService = userService;
        mLoginSource = loginSource;
        mSplashSource = splashSource;
    }

    @Override
    public void login(final LoginUser loginUser) {
        mView.showLoginStart();
        mView.hideSoftInputMethod();
        mUserService.login(loginUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoginFailed();
                    }

                    @Override
                    public void onNext(Response<User> userResponse) {
                        User user = userResponse.getData();
                        mView.closeLoginLoading();
                        mLoginSource.saveUser(user);
                    }
                });
    }

    @Override
    public void start() {

    }
}
