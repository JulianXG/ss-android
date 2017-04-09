/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.Comment;
import cn.kalyter.ss.model.Image;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.TrendsRecyclerAdapter;
import cn.kalyter.ss.util.Util;
import cn.kalyter.ss.view.MicroblogDetailActivity;
import cn.kalyter.ss.view.NewActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.kalyter.ss.config.Config.ALREADY_LATEST;
import static cn.kalyter.ss.config.Config.LOAD_SUCCESS;
import static cn.kalyter.ss.config.Config.NO_MORE;
import static cn.kalyter.ss.config.Config.REFRESH_ERROR;

public class TrendsPresenter implements TrendsContract.Presenter,
        TrendsRecyclerAdapter.OnClickTrendsItemListener {
    private static final String TAG = "TrendsPresenter";
    private TrendsContract.View mView;
    private TrendsRecyclerAdapter mTrendsRecyclerAdapter;
    private Context mContext;
    private MicroblogService mMicroblogService;
    private UserSource mUserSource;
    private int pageSize = 10;
    private OperateService mOperateService;

    public TrendsPresenter(TrendsContract.View view, Context context,
                           MicroblogService microblogService,
                           UserSource userSource,
                           OperateService operateService) {
        mView = view;
        mContext = context;
        mMicroblogService = microblogService;
        mUserSource = userSource;
        mTrendsRecyclerAdapter = new TrendsRecyclerAdapter(new ArrayList<Microblog>(), mContext);
        mOperateService = operateService;
    }

    @Override
    public void start() {
        mView.setAdapter(mTrendsRecyclerAdapter);
        loadUser();
        refresh();
        mTrendsRecyclerAdapter.setListener(this);
    }

    @Override
    public void loadUser() {
        User user = mUserSource.getUser();
        mView.showUser(user);
    }

    @Override
    public void refresh() {
        mView.setRefreshing(true);
        mMicroblogService.getTrends(mUserSource.getUserId(), pageSize, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Microblog>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            // TODO: 2017-4-3 0003 统一处理网络错误
                            Util.showNetworkError(mContext);
                        } else {
                            mView.showLoadingError();
                        }
                        mView.setRefreshing(false);
                    }

                    @Override
                    public void onNext(Response<List<Microblog>> listResponse) {
                        int result = mTrendsRecyclerAdapter.addLatestData(listResponse.getData());
                        switch (result) {
                            case LOAD_SUCCESS:
                                mView.showLoadingSuccess(listResponse.getData().size());
                                mView.moveToPosition(0);
                                break;
                            case ALREADY_LATEST:
                                mView.showAlreadyLatest();
                                break;
                            case REFRESH_ERROR:
                                Log.e(TAG, "onNext: 加载失败");
                        }
                        mView.setRefreshing(false);
                    }
                });
    }

    @Override
    public void loadMore() {
        mMicroblogService.getTrends(mUserSource.getUserId(), pageSize, mTrendsRecyclerAdapter.getOldestId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Microblog>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            // TODO: 2017-4-3 0003 统一处理网络错误
                            Util.showNetworkError(mContext);
                        } else {
                            mView.showLoadingError();
                        }
                        mView.showUpLoadMore(false);
                    }

                    @Override
                    public void onNext(Response<List<Microblog>> listResponse) {
                        int result = mTrendsRecyclerAdapter.addMoreData(listResponse.getData());
                        switch (result) {
                            case LOAD_SUCCESS:
                                mView.showLoadingSuccess(listResponse.getData().size());
                                break;
                            case NO_MORE:
                                mView.showNoMore();
                                break;
                            case REFRESH_ERROR:
                                Log.e(TAG, "onNext: 加载失败");
                        }
                        mView.showUpLoadMore(false);
                    }
                });
    }

    @Override
    public void commentMicroblog(long microblogId, String content) {
        Comment comment = new Comment();
        comment.setMicroblogId((int) microblogId);
        comment.setUserId(mUserSource.getUserId());
        comment.setCreateTime(new Date());
        comment.setContent(content);
        mOperateService.postComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.showCommentSuccess();
                        refresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showCommentFail();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void setLikeStatus(final int position, final long microblogId) {
        mOperateService.likeMicroblog(mUserSource.getUserId(), microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        Microblog microblog = mTrendsRecyclerAdapter.getItem(position);
                        microblog.setLikeStatus(1);
                        int count = microblog.getLikeCount();
                        microblog.setLikeCount(++count);
                        mTrendsRecyclerAdapter.changeItem(position, microblog);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.showNetworkError(mContext);
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void setCancelStatus(final int position, long microblogId) {
        mOperateService.cancelLike(mUserSource.getUserId(), microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        Microblog microblog = mTrendsRecyclerAdapter.getItem(position);
                        microblog.setLikeStatus(0);
                        int count = microblog.getLikeCount();
                        microblog.setLikeCount(--count);
                        mTrendsRecyclerAdapter.changeItem(position, microblog);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.showNetworkError(mContext);
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void showComments(long microblogId) {
        mView.showComments(microblogId);
    }

    @Override
    public void showRepost(long microblogId) {
        Intent intent = new Intent(mContext, NewActivity.class);
        intent.putExtra(Config.KEY_MICROBLOG_ID, microblogId);
        mContext.startActivity(intent);
    }

    @Override
    public void showDetail(long microblogId) {
        Intent intent = new Intent();
        intent.putExtra(Config.INTENT_MICROBLOG_ID, microblogId);
        intent.putExtra(Config.INTENT_OPEN_TYPE, Config.FROM_DETAIL);
        intent.setClass(mContext, MicroblogDetailActivity.class);
        mContext.startActivity(intent);
    }
}
