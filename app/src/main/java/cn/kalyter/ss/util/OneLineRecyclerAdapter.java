package cn.kalyter.ss.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.OneLineWrapper;
import cn.kalyter.ss.model.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kalyter on 2017-4-4 0004.
 */

public class OneLineRecyclerAdapter extends RecyclerView.Adapter<OneLineViewHolder> {
    private static final String TAG = "OneLineRecyclerAdapter";
    private List<OneLineWrapper> mData;
    private Context mContext;

    public OneLineRecyclerAdapter(Context context) {
        mData = new ArrayList<>();
        mContext = context;
    }

    @Override
    public OneLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext)
                .inflate(R.layout.oneline_item, parent, false);
        return new OneLineViewHolder(root);
    }

    @Override
    public void onBindViewHolder(OneLineViewHolder holder, int position) {
        OneLineWrapper data = mData.get(position);
        data = Util.handleNull(data);
        Glide.with(mContext)
                .load(data.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(mContext))
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .into(holder.mAvatar);
        holder.mPostTime.setText(Util.getPrettyDiffTime(data.getPostTime()));
        holder.mUsername.setText(data.getNickname());
        holder.mContent.setText(data.getContent());
    }

    public void setData(List<OneLineWrapper> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
