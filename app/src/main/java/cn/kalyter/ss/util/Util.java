package cn.kalyter.ss.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.kalyter.ss.R;
import cn.kalyter.ss.model.Image;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.view.ViewImagesActivity;

import static cn.kalyter.ss.config.Config.ONE_DAY;
import static cn.kalyter.ss.config.Config.ONE_HOUR;
import static cn.kalyter.ss.config.Config.ONE_MONTH;
import static cn.kalyter.ss.config.Config.ONE_YEAR;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public final class Util {
    private static final String TAG = "Util";

    /**
     * 处理类中属性的null情况
     * @param instance 类的实例
     * @param <T> 泛型
     * @return 返回处理过null的实例
     */
    public static  <T> T handleNull(T instance) {
        // 处理null情况，Integer，Long，Byte为-1，字符串为""，时间戳为0
        Class cls = instance.getClass();
        Field[] fields = cls.getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(instance) == null) {
                    Class type = field.getType();
                    String name = field.getName();
                    String setType = new StringBuilder()
                            .append("set")
                            .append(name.substring(0, 1).toUpperCase())
                            .append(name.substring(1))
                            .toString();
                    try {
                        Method method = cls.getMethod(setType, type);
                        if (type.getName().equals(Integer.class.getName()) ||
                                type.getName().equals(Long.class.getName()) ||
                                type.getName().equals(Byte.class.getName()) ) {
                            method.invoke(instance, 0);
                        } else if (type.getName().equals(String.class.getName())){
                            method.invoke(instance, "");
                        }
                    } catch (NoSuchMethodException ignored) {

                    }
                }
            }
            return instance;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "handleNull: ", e);
        }  catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showNetworkError(Context context) {
        Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 以一种漂亮的字符串格式，返回和当前时间点相差的时间
     * @param timestamp 需要比较的时间戳
     * @return 相差时间字符串
     */
    public static String getPrettyDiffTime(Long timestamp) {
        if (timestamp != 0) {
            long nowTimestamp = new Date().getTime();
            int diffSecond = (int) ((nowTimestamp - timestamp) / 1000);
            if (diffSecond < 60) {
                if (diffSecond < 1) {
                    return "刚刚";
                } else {
                    return diffSecond + "秒前";
                }
            } else if (diffSecond >= 60 && diffSecond < ONE_HOUR) {
                int minute = diffSecond / 60;
                return minute + "分钟前";
            } else if (diffSecond > ONE_HOUR && diffSecond < ONE_DAY) {
                return diffSecond / ONE_HOUR + "个小时前";
            } else if (diffSecond > ONE_DAY && diffSecond < ONE_MONTH) {
                return diffSecond / ONE_DAY + "天前";
            } else if (diffSecond > ONE_MONTH && diffSecond < ONE_YEAR) {
                return diffSecond / ONE_MONTH + "个月前";
            } else {
                return new Date(timestamp).toString();
            }
        }
        return "";
    }

    /**
     * 漂亮的显示微博的来源信息
     * @param device 发送微博的设备信息
     * @return 返回微博来源信息
     */
    public static String getPrettySource(String device) {
        if (device != null) {
            device = device.substring(0, 1).toUpperCase() + device.substring(1);
        } else {
            device = "";
        }
        return "来自 " + device;
    }

    /**
     * 显示微博
     * @param context context
     * @param view  微博显示的主View
     * @param microblog 微博数据
     */
    public static void showMicroblog(final Context context, View view, Microblog microblog) {
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        ImageView solvedStatus = (ImageView) view.findViewById(R.id.solve_status);
        int statusResId;
        if (microblog.getIssolved() == 0) {
            statusResId = R.drawable.to_be_solved;
        } else {
            statusResId = R.drawable.solved;
        }
        Glide.with(context)
                .load(statusResId)
                .centerCrop()
                .into(solvedStatus);
        Glide.with(context)
                .load(microblog.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .into(avatar);
        TextView username = (TextView) view.findViewById(R.id.username);
        username.setText(microblog.getNickname());
        TextView postTime = (TextView) view.findViewById(R.id.post_time);
        postTime.setText(getPrettyDiffTime(microblog.getPostTime()));
        TextView source = (TextView) view.findViewById(R.id.source);
        source.setText(getPrettySource(microblog.getDeviceName()));
        TextView content = (TextView) view.findViewById(R.id.content);
        content.setText(microblog.getContent());
        ImageView mImage0 = (ImageView) view.findViewById(R.id.image0);
        ImageView mImage1 = (ImageView) view.findViewById(R.id.image1);
        ImageView mImage2 = (ImageView) view.findViewById(R.id.image2);
        final List<Image> images = microblog.getImages();
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(mImage0);
        imageViews.add(mImage1);
        imageViews.add(mImage2);
        if (images != null && images.get(0) != null) {
            int length;
            if (images.size() < imageViews.size()) {
                length = images.size();
            } else {
                length = imageViews.size();
            }
            final List<String> stringImages = getImages(images);
            for (int i = 0; i < length; i++) {
                ImageView imageView = imageViews.get(i);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(images.get(i).getPath())
                        .placeholder(R.drawable.ic_panorama_black_24dp)
                        .centerCrop()
                        .into(imageView);
                final int index = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewImagesActivity.start(context, stringImages, index);
                    }
                });
            }
            for (int i = imageViews.size(); i > length; i--) {
                imageViews.get(i - 1).setVisibility(View.GONE);
            }
        }
        LinearLayout repostContainer = (LinearLayout) view.findViewById(R.id.repost_microblog_container);
        if (microblog.getRootMicroblog() != null) {
            repostContainer.setVisibility(View.VISIBLE);
            String repostContent = String.format("@%s:%s",
                    microblog.getRootMicroblog().getNickname(), microblog.getRootMicroblog().getContent());
            TextView repostContentText = (TextView) view.findViewById(R.id.repost_content);
            repostContentText.setText(repostContent);
            List<Image> repostImages = microblog.getRootMicroblog().getImages();
            List<ImageView> repostImageViews = new ArrayList<>();
            ImageView repostView0 = (ImageView) view.findViewById(R.id.repost_image0);
            ImageView repostView1 = (ImageView) view.findViewById(R.id.repost_image1);
            ImageView repostView2 = (ImageView) view.findViewById(R.id.repost_image2);
            repostImageViews.add(repostView0);
            repostImageViews.add(repostView1);
            repostImageViews.add(repostView2);
            if (repostImages != null && repostImages.get(0) != null) {
                int length;
                if (repostImages.size() < repostImageViews.size()) {
                    length = repostImages.size();
                } else {
                    length = repostImageViews.size();
                }
                final List<String> stringImages = getImages(repostImages);
                for (int i = 0; i < length; i++) {
                    ImageView imageView = repostImageViews.get(i);
                    imageView.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(repostImages.get(i).getPath())
                            .placeholder(R.drawable.ic_panorama_black_24dp)
                            .centerCrop()
                            .into(imageView);
                    final int index = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewImagesActivity.start(context, stringImages, index);
                        }
                    });
                }
            }
        } else {
            repostContainer.setVisibility(View.GONE);
        }
    }

    public static void showRepostMicroblog(final Context context,
                                           View view,
                                           Microblog microblog) {
        if (microblog != null) {
            LinearLayout repostContainer = (LinearLayout) view.findViewById(R.id.repost_microblog_container);
            repostContainer.setVisibility(View.VISIBLE);
            String repostContent;
            List<Image> repostImages;
            if (microblog.getRootMicroblog() == null) {
                repostContent = String.format("@%s:%s",
                        microblog.getNickname(), microblog.getContent());
                repostImages = microblog.getImages();
            } else {
                repostContent = String.format("@%s:%s",
                        microblog.getRootMicroblog().getNickname(), microblog.getRootMicroblog().getContent());
                repostImages = microblog.getRootMicroblog().getImages();
            }
            TextView repostContentText = (TextView) view.findViewById(R.id.repost_content);
            repostContentText.setText(repostContent);
            List<ImageView> repostImageViews = new ArrayList<>();
            ImageView repostView0 = (ImageView) view.findViewById(R.id.repost_image0);
            ImageView repostView1 = (ImageView) view.findViewById(R.id.repost_image1);
            ImageView repostView2 = (ImageView) view.findViewById(R.id.repost_image2);
            repostImageViews.add(repostView0);
            repostImageViews.add(repostView1);
            repostImageViews.add(repostView2);
            final List<String> stringImages = getImages(repostImages);
            if (repostImages != null && repostImages.get(0) != null) {
                for (int i = 0; i < repostImages.size(); i++) {
                    Glide.with(context)
                            .load(repostImages.get(i).getPath())
                            .placeholder(R.drawable.ic_panorama_black_24dp)
                            .centerCrop()
                            .into(repostImageViews.get(i));
                    final int index = i;
                    repostImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ViewImagesActivity.start(context, stringImages, index);
                        }
                    });
                }
            }
        }
    }

    public static List<String> getImages(List<Image> images) {
        List<String> result = new ArrayList<>();
        for (Image image : images) {
            result.add(image.getPath());
        }
        return result;
    }
}
