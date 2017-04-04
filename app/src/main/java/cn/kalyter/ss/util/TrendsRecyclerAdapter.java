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

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.model.Image;
import cn.kalyter.ss.model.Microblog;

import static cn.kalyter.ss.config.Config.ALREADY_LATEST;
import static cn.kalyter.ss.config.Config.LOAD_SUCCESS;
import static cn.kalyter.ss.config.Config.NO_MORE;
import static cn.kalyter.ss.config.Config.ONE_DAY;
import static cn.kalyter.ss.config.Config.ONE_HOUR;
import static cn.kalyter.ss.config.Config.ONE_MONTH;
import static cn.kalyter.ss.config.Config.ONE_YEAR;
import static cn.kalyter.ss.config.Config.REFRESH_ERROR;

public class TrendsRecyclerAdapter extends RecyclerView.Adapter<TrendsViewHolder> {
    private List<Microblog> mData;
    private Context mContext;
    private long mLatestId = 0;
    private long mOldestId;
    private OnClickTrendsItemListener mListener;

    public TrendsRecyclerAdapter(List<Microblog> data, Context context) {
        mData = data;
        mContext = context;
        Glide.with(mContext);
    }

    @Override
    public TrendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.trends_item, parent, false);
        return new TrendsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(final TrendsViewHolder holder, int position) {
        Microblog data = mData.get(position);
        final long microblogId = data.getId();
        Glide.with(mContext)
                .load(data.getAvatar())
                .transform(new GlideCircleTransform(mContext))
                .into(holder.mAvatar);
        if (data.getLikeCount() != null && data.getLikeCount() > 0) {
            holder.mLikeCount.setText(data.getLikeCount() + "");
        }
        if (data.getCommentCount() != null && data.getCommentCount() > 0) {
            holder.mCommentCount.setText(data.getCommentCount() + "");
        }
        if (data.getRepostCount() != null && data.getRepostCount() > 0) {
            holder.mRepostCount.setText(data.getRepostCount() + "");
        }
        holder.mContent.setText(data.getContent());
        if (data.getLikeStatus() != null && data.getLikeStatus() == 1) {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_thumb_up_gray_24dp);
            Drawable tint = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(tint, ContextCompat.getColor(mContext, R.color.already_like));
            holder.mLikeImage.setImageDrawable(tint);
            holder.mLikeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setCancelStatus(holder.getAdapterPosition(), microblogId);
                }
            });
        } else {
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_thumb_up_gray_24dp);
            Drawable tint = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(tint, ContextCompat.getColor(mContext, R.color.gray));
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
        holder.mUsername.setText(data.getNickname());
        holder.mPostTime.setText(Util.getPrettyDiffTime(data.getPostTime()));
        holder.mSource.setText("来自 " + data.getDevice());
        List<Image> images = data.getImages();
        if (images != null) {
            if (images.size() >= 1) {
                if (images.get(0) == null) {
                    return;
                }
                holder.mImage0.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(images.get(0).getPath())
                        .centerCrop()
                        .crossFade()
                        .into(holder.mImage0);
            }
            if (images.size() >= 2) {
                holder.mImage1.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(images.get(1).getPath())
                        .centerCrop()
                        .crossFade()
                        .into(holder.mImage1);
            }
            if (images.size() >= 3) {
                holder.mImage2.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(images.get(2).getPath())
                        .centerCrop()
                        .crossFade()
                        .into(holder.mImage2);
            }
        }
    }

    public void changeItem(int position, Microblog microblog) {
        mData.set(position, microblog);
        notifyItemChanged(position);
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
                return ALREADY_LATEST;
            }
        }
        return REFRESH_ERROR;
    }

    public int addMoreData(List<Microblog> data) {
        if (data.size() > 0) {
            long latestId = data.get(0).getId();
            if (latestId < mOldestId) {
                mData.addAll(mData.size(), data);
                mOldestId = data.get(data.size() - 1).getId();
                notifyItemRangeInserted(mData.size() - 1, data.size());
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
    }

}
