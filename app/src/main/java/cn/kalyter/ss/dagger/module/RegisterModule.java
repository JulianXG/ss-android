package cn.kalyter.ss.dagger.module;

import android.app.ProgressDialog;
import android.content.Context;

import cn.kalyter.ss.contract.RegisterContract;
import cn.kalyter.ss.data.remote.api.UserService;
import cn.kalyter.ss.presenter.RegisterPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

@Module
public class RegisterModule {
    private RegisterContract.View mView;
    private Context mContext;

    public RegisterModule(RegisterContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    RegisterContract.Presenter providePresenter(UserService userService) {
        return new RegisterPresenter(mView, mContext, userService);
    }

    @Provides
    ProgressDialog provideProgressDialog() {
        return new ProgressDialog(mContext);
    }
}
