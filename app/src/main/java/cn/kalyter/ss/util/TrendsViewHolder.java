/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.ss.R;

public class TrendsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.post_time)
    TextView mPostTime;
    @BindView(R.id.source)
    TextView mSource;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.image0)
    ImageView mImage0;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.image2)
    ImageView mImage2;
    @BindView(R.id.repost)
    TextView mRepostCount;
    @BindView(R.id.comment)
    TextView mCommentCount;
    @BindView(R.id.like)
    TextView mLikeCount;
    @BindView(R.id.like_image)
    ImageView mLikeImage;
    @BindView(R.id.like_container)
    RelativeLayout mLikeContainer;
    @BindView(R.id.comment_container)
    RelativeLayout mCommentContainer;
    @BindView(R.id.repost_container)
    RelativeLayout mRepostContainer;
    @BindView(R.id.trend_container)
    LinearLayout mTrendContainer;

    public TrendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
