package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.SettingsModule;
import cn.kalyter.ss.view.SettingsActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Component(modules = SettingsModule.class, dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface SettingsComponent {
    void inject(SettingsActivity activity);
}
