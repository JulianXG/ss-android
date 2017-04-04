package cn.kalyter.ss.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import cn.kalyter.ss.R;

import static cn.kalyter.ss.config.Config.ONE_DAY;
import static cn.kalyter.ss.config.Config.ONE_HOUR;
import static cn.kalyter.ss.config.Config.ONE_MONTH;
import static cn.kalyter.ss.config.Config.ONE_YEAR;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public final class Util {
    private static final String TAG = "Util";

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
                    Method method = cls.getMethod(setType, type);
                    if (type.getName().equals(Integer.class.getName()) ||
                            type.getName().equals(Long.class.getName()) ||
                            type.getName().equals(Byte.class.getName())) {
                        method.invoke(instance, 0);
                    } else if (type.getName().equals(String.class.getName())){
                        method.invoke(instance, "");
                    }
                }
            }
            return instance;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "handleNull: ", e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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
            int second = diffSecond % 60;
            String result = minute + "分钟";
            if (second > 0) {
                result += second + "秒";
            }
            result += "前";
            return result;
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
}
