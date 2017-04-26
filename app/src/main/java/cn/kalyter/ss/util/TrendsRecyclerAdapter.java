/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.model.Microblog;

import static cn.kalyter.ss.config.Config.ALREADY_LATEST;
import static cn.kalyter.ss.config.Config.LOAD_SUCCESS;
import static cn.kalyter.ss.config.Config.NO_MORE;
import static cn.kalyter.ss.config.Config.REFRESH_ERROR;

public class TrendsRecyclerAdapter extends RecyclerView.Adapter<TrendsViewHolder> {
    private static final String TAG = "TrendsRecyclerAdapter";
    private List<Microblog> mData;
    private Context mContext;
    private long mLatestId = 0;
    private long mOldestId;
    private OnClickTrendsItemListener mListener;

    public TrendsRecyclerAdapter(List<Microblog> data,
                                 Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public TrendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext)
                .inflate(R.layout.trends_item, parent, false);
        return new TrendsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final TrendsViewHolder holder, int position) {
        Microblog data = mData.get(position);
        final long microblogId = data.getId();
        if (data.getLikeCount() != null && data.getLikeCount() > 0) {
            holder.mLikeCount.setText(data.getLikeCount() + "");
        } else {
            holder.mLikeCount.setText(mContext.getString(R.string.like));
        }
        if (data.getCommentCount() != null && data.getCommentCount() > 0) {
            holder.mCommentCount.setText(data.getCommentCount() + "");
        } else {
            holder.mCommentCount.setText(mContext.getString(R.string.comment));
        }
        if (data.getRepostCount() != null && data.getRepostCount() > 0) {
            holder.mRepostCount.setText(data.getRepostCount() + "");
        } else {
            holder.mRepostCount.setText(mContext.getString(R.string.repost));
        }

        if (data.getLikeStatus() != null && data.getLikeStatus() == 1) {
            Drawable drawable = ContextCompat.getDrawable(
                    mContext, R.drawable.ic_thumb_up_gray_24dp).mutate();
            Drawable tint = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(tint, ContextCompat.getColor(
                    mContext, R.color.already_like));
            holder.mLikeImage.setImageDrawable(tint);
            holder.mLikeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setCancelStatus(holder.getAdapterPosition(), microblogId);
                }
            });
        } else {
            Drawable drawable = ContextCompat.getDrawable(
                    mContext, R.drawable.ic_thumb_up_gray_24dp).mutate();
            Drawable tint = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(tint, ContextCompat.getColor(
                    mContext, R.color.gray));
            holder.mLikeImage.setImageDrawable(tint);
            holder.mLikeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setLikeStatus(holder.getAdapterPosition(), microblogId);
                }
            });
        }
        holder.mCommentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showComments(microblogId);
            }
        });
        holder.mRepostContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showRepost(microblogId);
            }
        });

        holder.mTrendContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showDetail(microblogId);
            }
        });
        mListener.onViewMicroblog(microblogId);
        Util.showMicroblog(mContext, holder.mMicroblogContainer, data);
    }

    public void changeItem(int position, Microblog microblog) {
        mData.set(position, microblog);
        notifyItemChanged(position, microblog);
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
                    if (data.get(i).getId() == mOldestId) {
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

    public long getLatestId() {
        return mLatestId;
    }

    public long getOldestId() {
        return mOldestId;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setListener(OnClickTrendsItemListener listener) {
        mListener = listener;
    }

    public interface OnClickTrendsItemListener {
        void setLikeStatus(int position, long microblogId);

        void setCancelStatus(int position, long microblogId);

        void showComments(long microblogId);

        void showRepost(long microblogId);

        void showDetail(long microblogId);

        void onViewMicroblog(long microblogId);
    }
}
