package cn.kalyter.ss.dagger.module;

import android.content.Context;

import cn.kalyter.ss.contract.MicroblogCenterContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.presenter.MicroblogCenterPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */
@Module
public class MicroblogCenterModule {
    private MicroblogCenterContract.View mView;
    private Context mContext;

    public MicroblogCenterModule(MicroblogCenterContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    MicroblogCenterContract.Presenter providePresenter(MicroblogService microblogService,
                                                       UserSource userSource,
                                                       OperateService operateService) {
        return new MicroblogCenterPresenter(mView, microblogService,
                userSource, operateService, mContext);
    }
}
