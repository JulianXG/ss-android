/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.LoginModule;
import cn.kalyter.ss.view.LoginActivity;
import dagger.Component;

@Component(modules = LoginModule.class,
        dependencies = {
        AppComponent.class,
        ApiComponent.class,
        RepositoryComponent.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
