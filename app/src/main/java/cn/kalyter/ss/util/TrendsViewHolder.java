/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.resource)
    TextView mResource;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.repost)
    LinearLayout mRepost;
    @BindView(R.id.comment)
    LinearLayout mComment;
    @BindView(R.id.like)
    LinearLayout mLike;

    public TrendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
