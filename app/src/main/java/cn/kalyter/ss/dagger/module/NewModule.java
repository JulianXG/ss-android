package cn.kalyter.ss.dagger.module;

import android.app.ProgressDialog;
import android.content.Context;

import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import cn.kalyter.ss.R;
import cn.kalyter.ss.contract.NewContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.presenter.NewPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Kalyter on 2017-4-4 0004.
 */
@Module
public class NewModule {
    private NewContract.View mView;
    private Context mContext;

    public NewModule(NewContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    NewContract.Presenter providePresenter(MicroblogService microblogService,
                                           UserSource userSource,
                                           OperateService operateService) {
        return new NewPresenter(
                mContext, mView, microblogService, userSource, operateService);
    }

    @Provides
    ImageLoader provideLoader() {
        return new cn.kalyter.ss.util.ImageLoader();
    }

    @Provides
    ImgSelConfig provideConfig(ImageLoader imageLoader) {
        return new ImgSelConfig.Builder(mContext, imageLoader)
                .title("选择图片")
                .titleBgColor(R.color.white)
                .needCamera(false)
                .maxNum(3)
                .build();
    }

    @Provides
    ProgressDialog provideProgress() {
        return new ProgressDialog(mContext);
    }
}
