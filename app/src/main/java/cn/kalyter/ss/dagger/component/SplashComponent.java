/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.SplashModule;
import cn.kalyter.ss.view.SplashActivity;
import dagger.Component;

@Component(modules = SplashModule.class, dependencies = {RepositoryComponent.class})
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
