package cn.kalyter.ss.dagger.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import cn.kalyter.ss.contract.MicroblogDetailContract;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.presenter.MicroblogDetailPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */
@Module
public class MicroblogDetailModule {
    private MicroblogDetailContract.View mView;
    private Context mContext;

    public MicroblogDetailModule(MicroblogDetailContract.View view,
                                 Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    MicroblogDetailContract.Presenter providePresenter(OperateService operateService,
                                                       MicroblogService microblogService) {
        return new MicroblogDetailPresenter(mView, operateService, microblogService, mContext);
    }
}
