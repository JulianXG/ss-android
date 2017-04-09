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
    @BindView(R.id.microblog_container)
    LinearLayout mMicroblogContainer;

    public TrendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
