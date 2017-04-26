package cn.kalyter.ss.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.MicroblogCenterContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerMicroblogCenterComponent;
import cn.kalyter.ss.dagger.module.MicroblogCenterModule;
import cn.kalyter.ss.model.Search;
import cn.kalyter.ss.util.RecycleViewDivider;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class MicroblogCenterActivity extends BaseActivity implements MicroblogCenterContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    BGARefreshLayout mRefresh;

    @Inject
    MicroblogCenterContract.Presenter mPresenter;

    private int type;
    private String keywords;
    private LinearLayoutManager mLinearLayoutManager;
    private Search mSearch = new Search();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getIntent().getIntExtra(Config.KEY_ADMIN_TYPE, 0);
        keywords = getIntent().getStringExtra(Config.KEY_ADMIN_KEYWORDS);
        if (keywords == null) {
            keywords = "";
        }
        mSearch.setType(type);
        mSearch.setKeyword(keywords);

        String title = getString(R.string.microblog_center);
        if (type == Config.NOT_VIEWED) {
            title = String.format("%s(%s)", title, "未浏览");
        } else if (type == Config.NOT_RESOLVED) {
            title = String.format("%s(%s)", title, "待解决");
        } else if (type == Config.ALREADY_RESOLVED) {
            title = String.format("%s(%s)", title, "已解决");
        }
        mTitle.setText(title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolbar.inflateMenu(R.menu.search);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearch();
                return true;
            }
        });
        mPresenter.start();
        mPresenter.refresh(mSearch);
    }

    @Override
    protected void injectComponent() {
        DaggerMicroblogCenterComponent.builder()
                .microblogCenterModule(new MicroblogCenterModule(this, this))
                .repositoryComponent(App.getRepositoryComponent())
                .apiComponent(App.getApiComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_microblog_center;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLinearLayoutManager);
        mRecycler.addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.divider)));
        mRecycler.setAdapter(adapter);
        mRefresh.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(this, true);
        mRefresh.setRefreshViewHolder(viewHolder);
        viewHolder.setLoadingMoreText(getString(R.string.up_load_more));
    }

    @Override
    public void moveToPosition(int position) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            mRecycler.scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = mRecycler.getChildAt(position - firstItem).getTop();
            mRecycler.scrollBy(0, top);
        } else {
            mRecycler.scrollToPosition(position);
        }
    }

    @Override
    public void showAlreadyLatest() {
        Toast.makeText(this, R.string.already_latest, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {
        Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingSuccess(int count) {
        Toast.makeText(this, "成功加载了" + count + "条信息！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpLoadMore(boolean isShow) {
        if (isShow) {
            mRefresh.beginLoadingMore();
        } else {
            mRefresh.endLoadingMore();
        }
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            mRefresh.beginRefreshing();
        } else {
            mRefresh.endRefreshing();
        }
    }

    @Override
    public void showSearch() {
        startActivity(new Intent(this, SearchMicroblogActivity.class));
        finish();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPresenter.refresh(mSearch);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.loadMore(mSearch);
        return true;
    }
}
