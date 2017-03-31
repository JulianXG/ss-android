/**
 * Created by Julian on 2016/9/19.
 */
package cn.kalyter.ss.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}