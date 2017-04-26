package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.MicroblogCenterModule;
import cn.kalyter.ss.view.MicroblogCenterActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Component(modules = MicroblogCenterModule.class, dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface MicroblogCenterComponent {
    void inject(MicroblogCenterActivity activity);
}
