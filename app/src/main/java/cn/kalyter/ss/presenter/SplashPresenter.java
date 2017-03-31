/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.presenter;

import android.content.Context;
import android.os.Handler;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import cn.kalyter.ss.contract.SplashContract;
import cn.kalyter.ss.data.local.SplashSource;
import cn.kalyter.ss.view.LoginActivity;
import cn.kalyter.ss.view.MainFrameActivity;

public class SplashPresenter implements SplashContract.Presenter {
    private static final String TAG = "SplashPresenter";

    private SplashContract.View mView;
    private SplashSource mSplashSource;
    private Context mContext;

    public SplashPresenter(SplashContract.View view, SplashSource splashSource, Context context) {
        mView = view;
        mSplashSource = splashSource;
        mContext = context;
    }

    @Override
    public void loadSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mSplashSource.getIsLogin()) {
                    mView.showNext(LoginActivity.class);
                } else {
                    mView.showNext(MainFrameActivity.class);
                }
                mView.closeSplash();
            }
        }, mSplashSource.getSplashLastTime());
    }

    @Override
    public void start() {
        PushManager.startWork(mContext, PushConstants.LOGIN_TYPE_API_KEY,
                "qvMu9LVY2Qt4um9B3fTbWxC3");
        loadSplash();
    }
}
