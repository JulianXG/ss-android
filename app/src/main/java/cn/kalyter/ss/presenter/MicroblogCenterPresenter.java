package cn.kalyter.ss.presenter;

import android.content.Context;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.util.List;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.MicroblogCenterContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.Search;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.AdminMicroblogRecyclerAdapter;
import cn.kalyter.ss.util.Util;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static cn.kalyter.ss.config.Config.ALREADY_LATEST;
import static cn.kalyter.ss.config.Config.LOAD_SUCCESS;
import static cn.kalyter.ss.config.Config.NO_MORE;
import static cn.kalyter.ss.config.Config.REFRESH_ERROR;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class MicroblogCenterPresenter implements MicroblogCenterContract.Presenter,
        AdminMicroblogRecyclerAdapter.OnCheckMicroblogStatusListener{
    private static final String TAG = "MicroblogCenterPresente";
    private MicroblogCenterContract.View mView;
    private MicroblogService mMicroblogService;
    private UserSource mUserSource;
    private OperateService mOperateService;
    private AdminMicroblogRecyclerAdapter mAdminMicroblogRecyclerAdapter;
    private Context mContext;

    public MicroblogCenterPresenter(MicroblogCenterContract.View view,
                                    MicroblogService microblogService,
                                    UserSource userSource,
                                    OperateService operateService,
                                    Context context) {
        mView = view;
        mMicroblogService = microblogService;
        mUserSource = userSource;
        mOperateService = operateService;
        mContext = context;
        mAdminMicroblogRecyclerAdapter = new AdminMicroblogRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mAdminMicroblogRecyclerAdapter.setOnCheckMicroblogStatusListener(this);
        mView.setAdapter(mAdminMicroblogRecyclerAdapter);
    }

    @Override
    public void refresh(Search search) {
        User user = mUserSource.getUser();
        mMicroblogService.getMicroblogsByConfig(
                user.getId(), Config.PAGE_SIZE, 0, search)
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
                        int result = mAdminMicroblogRecyclerAdapter.addLatestData(listResponse.getData());
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
    public void loadMore(Search search) {
        mMicroblogService.getMicroblogsByConfig(mUserSource.getUserId(),
                Config.PAGE_SIZE, mAdminMicroblogRecyclerAdapter.getOldestId(), search)
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
                        int result = mAdminMicroblogRecyclerAdapter.addMoreData(listResponse.getData());
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
    public void onCheckMicroblog(final int position, int microblogId, boolean checkStatus) {
        mOperateService.changeSolveStatus(microblogId, checkStatus ? 1 : 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.showNetworkError(mContext);
                    }

                    @Override
                    public void onNext(Response response) {
                        if (response.getCode() == Config.OK) {
                            Microblog microblog = mAdminMicroblogRecyclerAdapter.getItem(position);
                            if (microblog.getIssolved() == 1) {
                                microblog.setIssolved(0);
                            } else {
                                microblog.setIssolved(1);
                            }
                            mAdminMicroblogRecyclerAdapter.changeItem(position, microblog);
                        }
                    }
                });
    }

    @Override
    public void onViewMicroblog(final long microblogId) {
        mOperateService.markViewStatus(mUserSource.getUserId(), microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response>() {
                    @Override
                    public void call(Response response) {
                        Log.i(TAG, "call: microblogId为：" + microblogId + "已浏览过");
                    }
                });
    }
}
