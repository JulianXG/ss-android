/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.dagger.module;

import android.app.ProgressDialog;
import android.content.Context;

import cn.kalyter.ss.contract.LoginContract;
import cn.kalyter.ss.data.local.LoginSource;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.LoginPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginContract.View mView;
    private Context mActivityContext;

    public LoginModule(LoginContract.View view, Context context) {
        mView = view;
        mActivityContext = context;
    }

    @Provides
    LoginContract.View provideView() {
        return mView;
    }

    @Provides
    LoginContract.Presenter providePresenter(LoginContract.View view,
                                             UserService userService,
                                             LoginSource loginSource,
                                             SplashSource splashSource) {
        return new LoginPresenter(view, userService, loginSource, splashSource);
    }

    @Provides
    ProgressDialog provideProgressDialog() {
        return new ProgressDialog(mActivityContext);
    }

//    @Provides
//    Validator provideValidator() {
//        return new Validator(mActivityContext);
//    }
}
