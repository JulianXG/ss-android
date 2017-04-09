package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.NewModule;
import cn.kalyter.ss.dagger.module.RepositoryModule;
import cn.kalyter.ss.view.NewActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-4 0004.
 */

@Component(modules = NewModule.class, dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface NewComponent {
    void inject(NewActivity activity);
}
