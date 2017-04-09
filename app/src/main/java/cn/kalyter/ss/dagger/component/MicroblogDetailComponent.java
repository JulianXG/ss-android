package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.MicroblogDetailModule;
import cn.kalyter.ss.view.MicroblogDetailActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-4 0004.
 */
@Component(modules = MicroblogDetailModule.class,
        dependencies = {ApiComponent.class})
public interface MicroblogDetailComponent {
    void inject(MicroblogDetailActivity activity);
}
