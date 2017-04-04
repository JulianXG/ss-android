package cn.kalyter.ss.contract;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.Microblog;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public interface MicroblogDetailContract {
    interface View extends BaseView {
        void showDetail(Microblog microblog);
    }

    interface Presenter extends BasePresenter {
        void loadDetail(int microblogId);
    }
}
