package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface MeContract {
    interface View extends BaseView {
        void showAdmin();

        void showNormalUser();

        void showUser(User user);

        void showLogin();
    }

    interface Presenter extends BasePresenter {
        void loadUser();

        void logout();
    }
}
