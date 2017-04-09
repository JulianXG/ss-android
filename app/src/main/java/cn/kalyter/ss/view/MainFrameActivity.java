package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import javax.inject.Inject;

import butterknife.BindView;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.MainFrameContract;
import cn.kalyter.ss.dagger.component.DaggerMainFrameComponent;
import cn.kalyter.ss.dagger.module.MainFrameModule;

public class MainFrameActivity extends BaseActivity implements MainFrameContract.View,
        BottomNavigationBar.OnTabSelectedListener {
    private static final String TAG = "MainFrameActivity";

    @Inject
    MainFrameContract.Presenter mPresenter;
    @Inject
    FragmentManager mFragmentManager;

    @BindView(R.id.bar)
    BottomNavigationBar mBottomNavigator;

    private int lastPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBottomNavigator.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigator.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigator
                .addItem(new BottomNavigationItem(R.drawable.ic_grade_black_24dp, "动态"))
                .setActiveColor(R.color.colorPrimaryDark)
                .addItem(new BottomNavigationItem(R.drawable.ic_add_box_black_24dp, "新建"))
                .setActiveColor(R.color.colorPrimaryDark)
                .addItem(new BottomNavigationItem(R.drawable.ic_account_circle_black_24dp, "我的"))
                .setActiveColor(R.color.colorPrimaryDark)
                .setTabSelectedListener(this)
                .setFirstSelectedPosition(0)
                .initialise();
        mPresenter.start();
    }

    @Override
    protected void injectComponent() {
        DaggerMainFrameComponent.builder()
                .mainFrameModule(new MainFrameModule(this, this))
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_frame;
    }

    @Override
    public void showDefaultFragment() {
        mPresenter.toggleFragment(mFragmentManager, 0);
    }

    @Override
    public void showActivity(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void onTabSelected(int position) {
        // 将新增看成是一个按钮，而不是一个页面
        if (position == 1) {
            mBottomNavigator.selectTab(lastPosition);
            startActivity(new Intent(this, NewActivity.class));
        } else {
            lastPosition = position;
            mPresenter.toggleFragment(mFragmentManager, position);
        }
    }



    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
