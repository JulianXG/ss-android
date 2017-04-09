package cn.kalyter.ss.presenter;

import android.content.Context;

import java.util.List;

import cn.kalyter.ss.contract.MicroblogDetailContract;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.OneLineWrapper;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.util.OneLineRecyclerAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public class MicroblogDetailPresenter implements MicroblogDetailContract.Presenter {
    private MicroblogDetailContract.View mView;
    private OperateService mOperateService;
    private MicroblogService mMicroblogService;
    private Context mContext;
    private OneLineRecyclerAdapter mRepostAdapter;
    private OneLineRecyclerAdapter mCommentAdapter;
    private long mMicroblogId;

    public MicroblogDetailPresenter(MicroblogDetailContract.View view,
                                    OperateService operateService,
                                    MicroblogService microblogService,
                                    Context context) {
        mView = view;
        mOperateService = operateService;
        mMicroblogService = microblogService;
        mContext = context;
        mRepostAdapter = new OneLineRecyclerAdapter(mContext);
        mCommentAdapter = new OneLineRecyclerAdapter(mContext);
    }

    @Override
    public void start() {
        mMicroblogId = mView.getMicroblogId();
        if (mMicroblogId == 0) {
            mView.showLoadDetailError();
        } else {
            loadMicroblog(mMicroblogId);
            loadComments(mMicroblogId);
            loadReposts(mMicroblogId);
            mView.setRepostAdapter(mRepostAdapter);
            mView.setCommentAdapter(mCommentAdapter);
        }
    }

    @Override
    public void loadMicroblog(long microblogId) {
        mMicroblogService.getMicroblog(microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Microblog>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadDetailError();
                    }

                    @Override
                    public void onNext(Response<Microblog> microblogResponse) {
                        mView.showMicroblog(microblogResponse.getData());
                    }
                });
    }

    @Override
    public void loadReposts(final long microblogId) {
        mOperateService.getReposts(microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<OneLineWrapper>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadDetailError();
                    }

                    @Override
                    public void onNext(Response<List<OneLineWrapper>> response) {
                        List<OneLineWrapper> data = response.getData();
                        if (data.size() > 0) {
                            mRepostAdapter.setData(data);
                            mView.showRepostsCount(data.size());
                        }
                    }
                });
    }

    @Override
    public void loadComments(long microblogId) {
        mOperateService.getCommentsById(microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<OneLineWrapper>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadDetailError();
                    }

                    @Override
                    public void onNext(Response<List<OneLineWrapper>> microblogResponse) {
                        List<OneLineWrapper> data = microblogResponse.getData();
                        if (data.size() > 0) {
                            mCommentAdapter.setData(data);
                            mView.showCommentsCount(data.size());
                        }
                    }
                });
    }

    @Override
    public void loadLikes(long microblogId) {

    }
}
