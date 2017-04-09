/**
 * Created by Julian on 2016/9/11.
 */
package cn.kalyter.ss.dagger;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import cn.kalyter.ss.dagger.component.ApiComponent;
import cn.kalyter.ss.dagger.component.AppComponent;
import cn.kalyter.ss.dagger.component.DaggerApiComponent;
import cn.kalyter.ss.dagger.component.DaggerAppComponent;
import cn.kalyter.ss.dagger.component.DaggerRepositoryComponent;
import cn.kalyter.ss.dagger.component.RepositoryComponent;
import cn.kalyter.ss.dagger.module.ApiModule;
import cn.kalyter.ss.dagger.module.AppModule;
import cn.kalyter.ss.dagger.module.RepositoryModule;

public class App extends Application {
    private static AppComponent mAppComponent;
    private static ApiComponent mApiComponent;
    private static RepositoryComponent mRepositoryComponent;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(mContext))
                .build();
        mApiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .build();
        mRepositoryComponent = DaggerRepositoryComponent.builder()
                .repositoryModule(new RepositoryModule())
                .appComponent(mAppComponent)
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static RepositoryComponent getRepositoryComponent() {
        return mRepositoryComponent;
    }

    public static ApiComponent getApiComponent() {
        return mApiComponent;
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
