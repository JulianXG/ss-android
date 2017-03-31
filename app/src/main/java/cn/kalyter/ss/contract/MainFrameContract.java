package cn.kalyter.ss.contract;

import android.support.v4.app.FragmentManager;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;

public interface MainFrameContract {
    interface Presenter extends BasePresenter{
        void toggleFragment(FragmentManager fragmentManager, int position);
    }

    interface View extends BaseView{
        void showDefaultFragment();

        void showActivity(Class activityClass);
    }
}