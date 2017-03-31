/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.TrendsModule;
import cn.kalyter.ss.view.TrendsFragment;
import dagger.Component;

@Component(modules = TrendsModule.class)
public interface TrendsComponent {
    void inject(TrendsFragment trendsFragment);
}
