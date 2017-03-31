/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.contract.SplashContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.presenter.SplashPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {
    private SplashContract.View mView;
    private Context mContext;

    public SplashModule(SplashContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    SplashContract.Presenter providePresenter(SplashSource splashSource) {
        return new SplashPresenter(mView, splashSource, mContext);
    }
}
