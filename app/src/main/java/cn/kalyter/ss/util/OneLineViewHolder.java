package cn.kalyter.ss.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.ss.R;

/**
 * Created by Kalyter on 2017-4-7 0007.
 */

public class OneLineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.post_time)
    TextView mPostTime;
    @BindView(R.id.content)
    TextView mContent;

    public OneLineViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
