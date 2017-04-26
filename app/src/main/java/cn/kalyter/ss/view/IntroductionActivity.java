package cn.kalyter.ss.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class IntroductionActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.introduction);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
