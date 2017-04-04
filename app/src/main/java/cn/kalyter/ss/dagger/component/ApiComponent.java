/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.dagger.component;

import cn.kalyter.ss.dagger.module.ApiModule;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.data.remote.api.UserService;
import dagger.Component;

@Component(modules = ApiModule.class)
public interface ApiComponent {
    UserService getLoginService();

    MicroblogService getMicroblogService();

    OperateService getOperate();
}
