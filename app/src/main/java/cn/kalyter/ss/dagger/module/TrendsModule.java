/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.presenter.TrendsPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class TrendsModule {
    private TrendsContract.View mView;

    private Context mContext;

    public TrendsModule(TrendsContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    TrendsContract.Presenter providePresenter() {
        return new TrendsPresenter(mView, mContext);
    }
}
