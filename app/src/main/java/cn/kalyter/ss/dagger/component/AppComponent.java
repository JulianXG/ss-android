/**
 * Created by Julian on 2016/9/19.
 */
package cn.kalyter.ss.dagger.component;

import android.content.Context;

import cn.kalyter.ss.dagger.module.AppModule;
import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {

    Context getContext();
}