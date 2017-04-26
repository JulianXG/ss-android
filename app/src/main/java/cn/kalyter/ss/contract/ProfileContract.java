package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface ProfileContract {
    interface View extends BaseView {
        void showUser(User user);
    }

    interface Presenter extends BasePresenter {
        void loadUser();
    }
}
