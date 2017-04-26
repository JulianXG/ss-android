package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.RegisterModule;
import cn.kalyter.ss.dagger.module.RepositoryModule;
import cn.kalyter.ss.view.RegisterActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

@Component(modules = RegisterModule.class, dependencies = ApiComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}
