package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface ChangePasswordContract {
    interface View extends BaseView {
        void showChanging();

        void showChangeSuccess();

        void showChangeError();

        void showLogin();
    }

    interface Presenter extends BasePresenter {
        void changePassword(String newPassword);
    }
}
