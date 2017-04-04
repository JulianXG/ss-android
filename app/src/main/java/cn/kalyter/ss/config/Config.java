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
    public static final String MODEL = Build.BRAND + " " + Build.MODEL;

    // 刷新状态
    public static final int REFRESH_ERROR = -1;
    public static final int ALREADY_LATEST = 0;
    public static final int LOAD_SUCCESS = 2;
    public static final int NO_MORE = 3;
}
