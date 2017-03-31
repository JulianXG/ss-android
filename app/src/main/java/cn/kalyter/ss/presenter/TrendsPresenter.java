/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.util.TrendsRecyclerAdapter;

public class TrendsPresenter implements TrendsContract.Presenter {
    private TrendsContract.View mView;
    private TrendsRecyclerAdapter mTrendsRecyclerAdapter;
    private Context mContext;

    public TrendsPresenter(TrendsContract.View view, Context context) {
        mView = view;
        mContext = context;
    }

    @Override
    public void start() {
        Date now = new Date();
        List<Microblog> data = new ArrayList<>();
        Microblog microblog = new Microblog();
        microblog.setPostTime(new Date(now.getTime() - (3 * 60 * 1000)));
        microblog.setContent(" A教学楼旁的井盖没了，很久无人维修，导致夜晚行人很容易摔跤");
        microblog.setImage("http://s15.sinaimg.cn/middle/6d068c34xbff6757ccc4e&690  ");
        Microblog microblog1 = new Microblog();
        microblog1.setPostTime(new Date(now.getTime() - (4 * 60 * 1000)));
        data.add(microblog);
        data.add(microblog1);
        mTrendsRecyclerAdapter = new TrendsRecyclerAdapter(data, mContext);
        mView.setAdapter(mTrendsRecyclerAdapter);
    }
}
