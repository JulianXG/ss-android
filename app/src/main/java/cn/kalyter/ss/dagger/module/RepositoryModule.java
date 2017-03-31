/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.data.local.LoginSource;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.repository.LoginRepository;
import cn.kalyter.ss.data.local.repository.SplashRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    LoginSource provideLoginSource() {
        return new LoginRepository();
    }

    @Provides
    SplashSource provideSplashSource(Context context) {
        return new SplashRepository(context);
    }
}
