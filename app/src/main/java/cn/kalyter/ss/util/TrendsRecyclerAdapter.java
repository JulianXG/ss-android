/**
 * Created by Kalyter on 12/11/2016.
 */
package cn.kalyter.ss.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.view.TrendsFragment;

public class TrendsRecyclerAdapter extends RecyclerView.Adapter<TrendsViewHolder> {
    private List<Microblog> mData;
    private Context mContext;

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
    public void onBindViewHolder(TrendsViewHolder holder, int position) {
        Microblog data = mData.get(position);
        Date now = new Date();
        Glide.with(mContext)
                .load("http://img.qq745.com/uploads/allimg/151022/1-151022193521.jpg")
                .transform(new GlideCircleTransform(mContext))
                .into(holder.mAvatar);
        holder.mUsername.setText("李四");
        holder.mPostTime.setText((now.getTime() - data.getPostTime().getTime())/(60 * 1000) + "分钟之前");
        holder.mResource.setText("来自于三星S7");
        holder.mContent.setText(data.getContent());
        Glide.with(mContext)
                .load(data.getImage())
                .centerCrop()
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
