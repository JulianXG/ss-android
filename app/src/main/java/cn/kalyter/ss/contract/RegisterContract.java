package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface RegisterContract {
    interface View extends BaseView {
        void showRegistering();

        void showRegisterSuccess();

        void showRegisterError();

        void showValidateError();

        void showPasswordConfirmError();

        void showUsernameConflict();

        void showUsernameAvailable();
    }

    interface Presenter extends BasePresenter {
        void register(User user);

        void validateUsername(String username);
    }
}
