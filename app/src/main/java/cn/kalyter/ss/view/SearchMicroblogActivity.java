package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.config.Config;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class SearchMicroblogActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.keywords)
    EditText mKeywords;
    @BindView(R.id.spinner)
    AppCompatSpinner mSpinner;
    @BindView(R.id.search)
    Button mSearch;

    @OnClick(R.id.search)
    void search() {
        showMicroblogCenter(mSpinner.getSelectedItemPosition(),
                mKeywords.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.search);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMicroblogCenter(0, "");
            }
        });

        mSpinner.getSelectedItemPosition();
    }


    public void showMicroblogCenter(int type, String keywords) {
        Intent intent = new Intent(this, MicroblogCenterActivity.class);
        intent.putExtra(Config.KEY_ADMIN_TYPE, type);
        intent.putExtra(Config.KEY_ADMIN_KEYWORDS, keywords);
        startActivity(intent);
        finish();
    }

    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_microblog;
    }
}
