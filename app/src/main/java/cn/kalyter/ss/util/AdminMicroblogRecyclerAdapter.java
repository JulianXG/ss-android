package cn.kalyter.ss.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.ss.R;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.model.Microblog;

import static cn.kalyter.ss.config.Config.ALREADY_LATEST;
import static cn.kalyter.ss.config.Config.LOAD_SUCCESS;
import static cn.kalyter.ss.config.Config.NO_MORE;
import static cn.kalyter.ss.config.Config.REFRESH_ERROR;

/**
 * Created by Kalyter on 2017-4-16 0016.
 * 管理员界面相关的RecyclerAdapter
 */

public class AdminMicroblogRecyclerAdapter extends RecyclerView.Adapter<AdminMicroblogRecyclerAdapter.ViewHolder> {
    private List<Microblog> mData;
    private Context mContext;
    private OnCheckMicroblogStatusListener mOnCheckMicroblogStatusListener;
    private long mLatestId = 0;
    private long mOldestId;

    public AdminMicroblogRecyclerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_admin_microblog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Microblog microblog = mData.get(position);
        Util.showMicroblog(mContext, holder.itemView, microblog);
        if (microblog.getIssolved() == 1) {
            holder.mSwitchStatus.setChecked(true);
        } else {
            holder.mSwitchStatus.setChecked(false);
        }
        holder.mSwitchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCheckMicroblogStatusListener.onCheckMicroblog(position, microblog.getId(), holder.mSwitchStatus.isChecked());
            }
        });

        holder.mMicroblogContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCheckMicroblogStatusListener.onViewMicroblog(microblog.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Microblog getItem(int position) {
        return mData.get(position);
    }

    public int addLatestData(List<Microblog> data) {
        if (data.size() > 0) {
            long latestId = data.get(0).getId();
            if (latestId > mLatestId) {
                //  判断是否存在两个列表重合的情况，去除重合的元素
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getId() == mLatestId) {
                        data = data.subList(0, i);
                        break;
                    }
                }
                mData.addAll(0, data);
                notifyItemRangeInserted(0, data.size());
                mLatestId = latestId;
                mOldestId = data.get(data.size() - 1).getId();
                return LOAD_SUCCESS;
            }  else {
                mData = mData.subList(data.size(), mData.size());
                mData.addAll(0, data);
                notifyDataSetChanged();
                return ALREADY_LATEST;
            }
        }
        return REFRESH_ERROR;
    }


    public int addMoreData(List<Microblog> data) {
        if (data.size() > 0) {
            long latestId = data.get(0).getId();
            if (latestId < mOldestId) {
                int prePosition = mData.size();
                mData.addAll(mData.size(), data);
                mOldestId = data.get(data.size() - 1).getId();
                notifyItemRangeInserted(prePosition, data.size());
                return LOAD_SUCCESS;
            }
        }
        return NO_MORE;
    }

    public long getOldestId() {
        return mOldestId;
    }

    public void setData(List<Microblog> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void changeItem(int position, Microblog microblog) {
        mData.set(position, microblog);
        notifyItemChanged(position, microblog);
    }

    public void setOnCheckMicroblogStatusListener(OnCheckMicroblogStatusListener onCheckMicroblogStatusListener) {
        mOnCheckMicroblogStatusListener = onCheckMicroblogStatusListener;
    }

    public interface OnCheckMicroblogStatusListener {
        void onCheckMicroblog(int position, int microblogId, boolean checkStatus);

        void onViewMicroblog(long microblogId);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.post_time)
        TextView mPostTime;
        @BindView(R.id.source)
        TextView mSource;
        @BindView(R.id.solve_status)
        ImageView mSolveStatus;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.image0)
        ImageView mImage0;
        @BindView(R.id.image1)
        ImageView mImage1;
        @BindView(R.id.image2)
        ImageView mImage2;
        @BindView(R.id.repost_content)
        TextView mRepostContent;
        @BindView(R.id.repost_image0)
        ImageView mRepostImage0;
        @BindView(R.id.repost_image1)
        ImageView mRepostImage1;
        @BindView(R.id.repost_image2)
        ImageView mRepostImage2;
        @BindView(R.id.repost_microblog_container)
        LinearLayout mRepostMicroblogContainer;
        @BindView(R.id.microblog_container)
        LinearLayout mMicroblogContainer;
        @BindView(R.id.switch_status)
        Switch mSwitchStatus;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
