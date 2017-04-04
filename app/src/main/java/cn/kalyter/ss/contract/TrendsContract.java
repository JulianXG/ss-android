/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.User;

public interface TrendsContract {
    interface View extends BaseView {
        void showAlreadyLatest();

        void showNoMore();

        void showLoadingSuccess();

        void showLoadingError();

        void showUpLoadMore(boolean isShow);

        void setRefreshing(boolean isRefreshing);

        void showUser(User user);

        void setAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter extends BasePresenter {
        void loadUser();

        void refresh();

        void loadMore();
    }
}
