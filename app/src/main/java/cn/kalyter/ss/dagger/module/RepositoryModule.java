/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.data.local.repository.UserRepository;
import cn.kalyter.ss.data.local.repository.SplashRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    UserSource provideUserSource(Context context) {
        return new UserRepository(context);
    }

    @Provides
    SplashSource provideSplashSource(Context context) {
        return new SplashRepository(context);
    }
}
