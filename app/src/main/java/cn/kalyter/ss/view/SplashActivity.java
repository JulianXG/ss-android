/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.SplashContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerSplashComponent;
import cn.kalyter.ss.dagger.module.SplashModule;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject
    SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void showNext(Class<?> activityClass) {
        Intent intent = new Intent(SplashActivity.this, activityClass);
        startActivity(intent);
    }

    @Override
    public void closeSplash() {
        finish();
    }

    @Override
    public void injectComponent() {
        DaggerSplashComponent.builder()
                .splashModule(new SplashModule(this, getApplicationContext()))
                .repositoryComponent(App.getRepositoryComponent())
                .build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}
