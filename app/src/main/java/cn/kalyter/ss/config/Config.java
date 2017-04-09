package cn.kalyter.ss.config;

import android.os.Build;

/**
 * Created by Kalyter on 2017-4-1 0001.
 */

public final class Config {
    public static final String COMMON_SP = "COMMON_SP";

    //  LoginRepository的配置常量
    public static final String LOGIN_SP = "LOGIN_SP";

    public static final int ONE_HOUR = 60 * 60;
    public static final int ONE_DAY = 24 * ONE_HOUR;
    public static final int ONE_MONTH = 30 * ONE_DAY;
    public static final int ONE_YEAR = 12 * ONE_MONTH;


    // HTTP请求返回码
    public static final int OK = 200;

    public static final byte MALE = 0;
    public static final byte FEMALE = 1;

    // 手机型号
    public static final String LOCAL_DEVICE = Build.BRAND + " " + Build.MODEL;

    // 刷新状态
    public static final int REFRESH_ERROR = -1;
    public static final int ALREADY_LATEST = 0;
    public static final int LOAD_SUCCESS = 2;
    public static final int NO_MORE = 3;

    //  Intent值
    public static final String INTENT_MICROBLOG_ID = "INTENT_MICROBLOG_ID";
    public static final String INTENT_OPEN_TYPE = "OPEN_TYPE";
    public static final int FROM_DETAIL = 100;
    public static final int FROM_COMMENT = 101;
    public static final String KEY_IMAGES = "IMAGES";
    public static final String KEY_IMAGE_INDEX = "IMAGE_INDEX";

    // Request Code值
    public static final int REQUEST_CODE_TAKE_PHOTO = 1000;
    public static final int REQUEST_CODE_EDIT_PHOTO = 1001;
    public static final int REQUEST_CODE_SELECT_PHOTO = 1002;
    public static final int REQUEST_CODE_PERMISSION = 2000;
    public static final int REQUEST_CODE_REPOST = 3000;
    public static final int TYPE_POST = 0;
    public static final int TYPE_REPOST = 1;

    public static final int FROM_ALBUM = 0;
    public static final int FROM_CAPTURE = 1;

    public static final int REPOST_TAB = 0;
    public static final int COMMENT_TAB = 1;

    // Argument
    public static final String KEY_ONELINE_ADAPTER_ARGUMENT = "ONELINE_ADAPTER_ARGUMENT";
    public static final String KEY_MICROBLOG_ID = "MICROBLOG_ID";
}
