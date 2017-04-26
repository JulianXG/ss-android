package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface SettingsContract {
    interface View extends BaseView {
        void showValidating();

        void showValidateOriginError();

        void showChangePassword();
    }

    interface Presenter extends BasePresenter {
        void validateOriginPassword(String password);

        void changePassword(String newPassword);
    }
}
