/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

public interface TrendsContract {
    interface View extends BaseView {
        void setAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter extends BasePresenter {
    }
}
