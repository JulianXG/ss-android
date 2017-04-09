/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseFragment;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerTrendsComponent;
import cn.kalyter.ss.dagger.module.TrendsModule;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.GlideCircleTransform;
import cn.kalyter.ss.util.RecycleViewDivider;

public class TrendsFragment extends BaseFragment implements TrendsContract.View,
        BGARefreshLayout.BGARefreshLayoutDelegate{
    @Inject
    TrendsContract.Presenter mPresenter;

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.trends_refresh)
    BGARefreshLayout mRefreshLayout;

    private AlertDialog mAlertDialog;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.VERTICAL, 40, getResources().getColor(R.color.divider)));

        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        mRefreshLayout.setRefreshViewHolder(viewHolder);
        viewHolder.setLoadingMoreText(getString(R.string.up_load_more));
        mPresenter.start();
    }

    @Override
    protected void injectComponent() {
        DaggerTrendsComponent.builder()
                .trendsModule(new TrendsModule(this, getContext()))
                .repositoryComponent(App.getRepositoryComponent())
                .apiComponent(App.getApiComponent())
                .appComponent(App.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trends;
    }

    @Override
    public void showAlreadyLatest() {
        Toast.makeText(getContext(), R.string.already_latest, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {
        Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingSuccess(int count) {
        Toast.makeText(getContext(), "成功加载了" + count + "条信息！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpLoadMore(boolean isShow) {
        if (isShow) {
            mRefreshLayout.beginLoadingMore();
        } else {
            mRefreshLayout.endLoadingMore();
        }
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            mRefreshLayout.beginRefreshing();
        } else {
            mRefreshLayout.endRefreshing();
        }
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .transform(new GlideCircleTransform(getContext()))
                .into(mAvatar);
        mUsername.setText(user.getNickname());
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showComments(final long microblogId) {
        if (mAlertDialog == null) {
            final EditText editText = new EditText(getContext());
            mAlertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("发表评论")
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!TextUtils.isEmpty(editText.getText())) {
                                mPresenter.commentMicroblog(microblogId, editText.getText().toString());
                                editText.setText("");
                            }
                        }
                    }).create();
        }
        mAlertDialog.show();
    }

    @Override
    public void showCommentSuccess() {
        Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentFail() {
        Toast.makeText(getContext(), "发表评论失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToPosition(int position) {
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            mRecyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(position);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPresenter.refresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.loadMore();
        return true;
    }
}
