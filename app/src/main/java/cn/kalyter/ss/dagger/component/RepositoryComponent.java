/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.RepositoryModule;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.local.SplashSource;
import dagger.Component;

@Component(modules = RepositoryModule.class, dependencies = AppComponent.class)
public interface RepositoryComponent {
    UserSource getLoginSource();

    SplashSource getSplashSource();
}
