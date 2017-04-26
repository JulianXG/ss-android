package cn.kalyter.ss.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.util.HealthyViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public class ViewImagesActivity extends BaseActivity {
    @BindView(R.id.frame)
    FrameLayout mFrameLayout;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.container)
    RelativeLayout mContainer;

    private HealthyViewPager mViewPager;

    @OnClick(R.id.close)
    void close() {
        finish();
    }

    private ImagePagerAdapter mImagePagerAdapter;
    private int count;

    public static void start(Context context, List<String> images, int imageIndex) {
        Intent intent = new Intent(context, ViewImagesActivity.class);
        intent.putExtra(Config.KEY_IMAGES, images.toArray(new String[]{}));
        intent.putExtra(Config.KEY_IMAGE_INDEX, imageIndex);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent data = getIntent();
        String[] images = data.getStringArrayExtra(Config.KEY_IMAGES);
        int imageIndex = data.getIntExtra(Config.KEY_IMAGE_INDEX, 0);
        count = images.length;
        mImagePagerAdapter = new ImagePagerAdapter(Arrays.asList(images), this);
        mViewPager = new HealthyViewPager(this);
        mViewPager.setAdapter(mImagePagerAdapter);

        mViewPager.setCurrentItem(imageIndex);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTitle.setText(String.format("%d/%d", position + 1, count));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mFrameLayout.addView(mViewPager);
    }

    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_images;
    }

    class ImagePagerAdapter extends PagerAdapter {
        private List<String> mData;
        private Context mContext;
        private List<View> mImageViews;

        public ImagePagerAdapter(List<String> data, Context context) {
            mData = data;
            mContext = context;
            mImageViews = new ArrayList<>(mData.size());
            for (int i = 0; i < mData.size(); i++) {
                RelativeLayout imageView = (RelativeLayout) LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_view_image, null);
                mImageViews.add(imageView);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeViewAt(position);
            mImageViews.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = (ImageView) mImageViews.get(position).findViewById(R.id.photo);
            Glide.with(mContext)
                    .load(mData.get(position))
                    .thumbnail(0.1f)
                    .fitCenter()
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .into(view);
            new PhotoViewAttacher(view);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
