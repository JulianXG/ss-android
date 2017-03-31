/**
 * Created by Kalyter on 2016/10/25.
 */
package cn.kalyter.ss.contract;


import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.LoginUser;

public interface LoginContract {
    interface View extends BaseView {
        void showLoginStart();

        void showLoginFailed();

        void showValidateError(String message);

        void hideSoftInputMethod();

        void showMainFrame();

        void closeLoginLoading();
    }

    interface Presenter extends BasePresenter {
        void login(LoginUser loginUser);
    }
}
