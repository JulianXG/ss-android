/**
 * Created by Julian on 2016/9/13.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.MainFrameModule;
import cn.kalyter.ss.view.MainFrameActivity;
import dagger.Component;

@Component(modules = MainFrameModule.class)
public interface MainFrameComponent {

    void inject(MainFrameActivity activity);

}