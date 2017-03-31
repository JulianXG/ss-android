/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseFragment;
import cn.kalyter.ss.contract.TrendsContract;
import cn.kalyter.ss.dagger.component.DaggerTrendsComponent;
import cn.kalyter.ss.dagger.module.TrendsModule;
import cn.kalyter.ss.util.GlideCircleTransform;

public class TrendsFragment extends BaseFragment implements TrendsContract.View {
    @Inject
    TrendsContract.Presenter mPresenter;

    @BindView(R.id.roll_pager)
    RollPagerView mRollPagerView;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    public final static String AVATAR = "http://p0.meituan.net/avatar/1dff8c38406d4d6b6540c69503f409d357171.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRollPagerView.setAdapter(new LoopPagerAdapter(mRollPagerView) {
            private String[] mData = new String[]{
                    "http://news.ruc.edu.cn/wp-content/uploads/2016/11/302859044025939832.jpg",
                    "http://news.ruc.edu.cn/wp-content/uploads/2016/11/image004-6.jpg",
                    "http://news.ruc.edu.cn/wp-content/uploads/2016/11/694822852143840672.jpg"
            };

            @Override
            public View getView(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                Glide.with(TrendsFragment.this)
                        .load(mData[position])
                        .centerCrop()
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getRealCount() {
                return mData.length;
            }
        });

        Glide.with(this)
                .load(AVATAR)
                .transform(new GlideCircleTransform(getContext()))
                .into(mAvatar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.start();
        return view;
    }

    @Override
    protected void injectComponent() {
        DaggerTrendsComponent.builder()
                .trendsModule(new TrendsModule(this, getContext()))
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trends;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
