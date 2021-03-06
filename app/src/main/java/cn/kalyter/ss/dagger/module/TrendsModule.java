/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
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
    TrendsContract.Presenter providePresenter(MicroblogService microblogService,
                                              UserSource userSource,
                                              OperateService operateService) {
        return new TrendsPresenter(mView, mContext,
                microblogService, userSource, operateService);
    }
}
