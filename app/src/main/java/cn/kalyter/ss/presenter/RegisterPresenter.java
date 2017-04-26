package cn.kalyter.ss.presenter;

import android.content.Context;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.RegisterContract;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private Context mContext;
    private UserService mUserService;

    public RegisterPresenter(RegisterContract.View view,
                             Context context,
                             UserService userService) {
        mView = view;
        mContext = context;
        mUserService = userService;
    }

    @Override
    public void start() {

    }

    @Override
    public void register(final User user) {
        mView.showRegistering();
        mView.showRegistering();
        mUserService.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.OK) {
                            mView.showRegisterSuccess();
                        } else if (response.getCode() == Config.ERROR_USERNAME_ALREADY_EXISTS) {
                            mView.showUsernameConflict();
                        }
                    }
                });
    }

    @Override
    public void validateUsername(String username) {
        mUserService.validateUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.ERROR_USERNAME_ALREADY_EXISTS) {
                            mView.showUsernameConflict();
                        } else if (response.getCode() == Config.OK) {
                            mView.showUsernameAvailable();
                        }
                    }
                });
    }
}
