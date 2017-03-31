/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.contract;


import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void showNext(Class<?> activityClass);

        void closeSplash();
    }

    interface Presenter extends BasePresenter {
        void loadSplash();
    }
}
