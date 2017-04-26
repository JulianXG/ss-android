package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.ProfileModule;
import cn.kalyter.ss.dagger.module.RepositoryModule;
import cn.kalyter.ss.view.ProfileActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Component(modules = ProfileModule.class, dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface ProfileComponent {
    void inject(ProfileActivity activity);
}
