package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.MicroblogDetailContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerMicroblogDetailComponent;
import cn.kalyter.ss.dagger.module.MicroblogDetailModule;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.util.OneLineRecyclerAdapter;
import cn.kalyter.ss.util.Util;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public class MicroblogDetailActivity extends BaseActivity implements MicroblogDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.microblog_container)
    LinearLayout mMicroblogContainer;
    @BindView(R.id.comment_recycler)
    RecyclerView mCommentRecycler;
    @BindView(R.id.repost_recycler)
    RecyclerView mRepostRecycler;
    @BindView(R.id.comment_title)
    TextView mCommentTitle;
    @BindView(R.id.repost_title)
    TextView mRepostTitle;

    @Inject
    MicroblogDetailContract.Presenter mPresenter;
    private long microblogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        LinearLayoutManager repostManager = new LinearLayoutManager(this);
        LinearLayoutManager commentManager = new LinearLayoutManager(this);
        mRepostRecycler.setLayoutManager(repostManager);
        mCommentRecycler.setLayoutManager(commentManager);
        microblogId = intent.getLongExtra(Config.INTENT_MICROBLOG_ID, 0);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.start();
    }

    @Override
    public long getMicroblogId() {
        return microblogId;
    }

    @Override
    public void showLoadDetailError() {
        Toast.makeText(this, R.string.load_detail_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMicroblog(Microblog microblog) {
        Util.showMicroblog(this, mMicroblogContainer, microblog);
    }

    @Override
    public void showCommentsCount(long count) {
        mCommentTitle.setText(String.format("%s(%d)",
                getString(R.string.comment), count));
    }

    @Override
    public void showRepostsCount(long count) {
        mRepostTitle.setText(String.format("%s(%d)",
                getString(R.string.repost), count));
    }

    @Override
    protected void injectComponent() {
        DaggerMicroblogDetailComponent.builder()
                .microblogDetailModule(new MicroblogDetailModule(this, this))
                .apiComponent(App.getApiComponent())
                .build().inject(this);
    }

    @Override
    public void setCommentAdapter(OneLineRecyclerAdapter adapter) {
        mCommentRecycler.setAdapter(adapter);
    }

    @Override
    public void setRepostAdapter(OneLineRecyclerAdapter adapter) {
        mRepostRecycler.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_microblog_detail;
    }
}
