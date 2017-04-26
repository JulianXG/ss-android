package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.MeModule;
import cn.kalyter.ss.view.MeFragment;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

@Component(modules = MeModule.class, dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface MeComponent {
    void inject(MeFragment fragment);
}
