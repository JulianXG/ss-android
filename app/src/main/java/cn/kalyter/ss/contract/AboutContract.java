package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface AboutContract {
    interface View extends BaseView {
        void showCheckUpdating();

        void showNoUpdate();
    }

    interface Presenter extends BasePresenter {

    }
}
