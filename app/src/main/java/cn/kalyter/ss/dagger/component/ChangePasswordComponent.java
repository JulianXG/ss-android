package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.ChangePasswordModule;
import cn.kalyter.ss.view.ChangePasswordActivity;
import dagger.Component;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Component(modules = ChangePasswordModule.class , dependencies = {
        ApiComponent.class,
        RepositoryComponent.class
})
public interface ChangePasswordComponent {
    void inject(ChangePasswordActivity activity);
}
