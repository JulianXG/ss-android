/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.presenter;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.Image;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.TrendsRecyclerAdapter;
import cn.kalyter.ss.util.Util;
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
        mTrendsRecyclerAdapter.addLatestData(mockData());
    }

    private List<Microblog> mockData() {
        List<Microblog> data = new ArrayList<>();
        Microblog microblog = new Microblog();
        microblog.setId(10);
        microblog.setDevice(Config.MODEL);
        microblog.setNickname("风过无痕");
        microblog.setAvatar("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=aladdin&url=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3Df0c5c08030d3d539c16807c70fb7c566%2F8ad4b31c8701a18bbef9f231982f07082838feba.jpg");
        microblog.setContent("Hello World!");
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setPath("https://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.sj33.cn%2Fuploads%2Fallimg%2F201302%2F1-130201105204.jpg&thumburl=https%3A%2F%2Fss2.bdstatic.com%2F70cFvnSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1012912279%2C3382694267%26fm%3D23%26gp%3D0.jpg");
        images.add(image);
        image = new Image();
        image.setPath("https://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201210%2F21%2F20121021202014_kAAr3.thumb.600_0.jpeg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2176570095%2C19101992%26fm%3D23%26gp%3D0.jpg");
        images.add(image);
        image = new Image();
        image.setPath("https://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic7.nipic.com%2F20100514%2F2158700_153225558098_2.jpg&thumburl=https%3A%2F%2Fss0.bdstatic.com%2F70cFuHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2877441215%2C2220934533%26fm%3D23%26gp%3D0.jpg");
        images.add(image);
        image = new Image();
        image.setPath("https://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Foss.gkstk.com%2Fimages%2F2017%2F2%2F23122610233.jpg&thumburl=https%3A%2F%2Fss0.bdstatic.com%2F70cFuHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2098044820%2C531013254%26fm%3D11%26gp%3D0.jpg");
        images.add(image);
        microblog.setImages(images);
        microblog.setLikeCount(10);
        microblog.setCommentCount(2);
        microblog.setRepostCount(100);
        microblog.setPostTime(new Date().getTime() - 3000);
        data.add(microblog);
        data.add(microblog);
        return data;
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
                                mView.showLoadingSuccess();
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
                                mView.showLoadingSuccess();
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

    }

    @Override
    public void showRepost(long microblogId) {

    }

    @Override
    public void showDetail(long microblogId) {

    }
}
