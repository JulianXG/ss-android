package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.AboutContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class AboutActivity extends BaseActivity implements AboutContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.introduction)
    LSettingItem mIntroduction;
    @BindView(R.id.check_update)
    LSettingItem mCheckUpdate;
    @BindView(R.id.feedback)
    LSettingItem mFeedback;
    @BindView(R.id.help)
    LSettingItem mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.about);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCheckUpdate.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                showCheckUpdating();
                Observable.just(0)
                        .delay(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                showNoUpdate();
                            }
                        });
            }
        });

        mFeedback.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(AboutActivity.this, FeedbackActivity.class));
            }
        });

        mHelp.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(AboutActivity.this, HelpActivity.class));
            }
        });

        mIntroduction.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(AboutActivity.this, IntroductionActivity.class));
            }
        });
    }

    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void showCheckUpdating() {
        Toast.makeText(this, "正在检查更新……", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoUpdate() {
        Toast.makeText(this, "已经是最新的版本了", Toast.LENGTH_SHORT).show();
    }
}
