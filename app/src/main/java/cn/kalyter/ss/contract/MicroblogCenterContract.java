package cn.kalyter.ss.contract;

import android.support.v7.widget.RecyclerView;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.Search;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public interface MicroblogCenterContract {
    interface View extends BaseView {
        void setAdapter(RecyclerView.Adapter adapter);

        void moveToPosition(int position);

        void showAlreadyLatest();

        void showNoMore();

        void showLoadingSuccess(int count);

        void showLoadingError();

        void showUpLoadMore(boolean isShow);

        void setRefreshing(boolean isRefreshing);

        void showSearch();
    }

    interface Presenter extends BasePresenter {
        void refresh(Search search);

        void loadMore(Search search);
    }
}
