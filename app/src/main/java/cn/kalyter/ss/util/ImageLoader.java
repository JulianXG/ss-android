package cn.kalyter.ss.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Kalyter on 2017-4-6 0006.
 */

public class ImageLoader implements com.yuyh.library.imgsel.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }
}
