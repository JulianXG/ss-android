package cn.kalyter.ss.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.contract.MainFrameContract;
import cn.kalyter.ss.view.NewActivity;
import cn.kalyter.ss.view.TrendsFragment;

public class MainFramePresenter implements MainFrameContract.Presenter {

    private static final String TAG = "MainFramePresenter";

    private MainFrameContract.View mView;
    private Context mContext;

    public MainFramePresenter(MainFrameContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Override
    public void toggleFragment(FragmentManager fragmentManager, int position) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentTransaction.hide(fragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        if (fragment != null) {
            fragmentTransaction.show(fragment);
        } else {
            switch (position) {
                case 0:
                    fragmentTransaction.add(R.id.content, new TrendsFragment(),
                            String.valueOf(position));
                    break;
                case 1:
                    mView.showActivity(NewActivity.class);
                    break;
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void start() {
        mView.showDefaultFragment();
    }
}
