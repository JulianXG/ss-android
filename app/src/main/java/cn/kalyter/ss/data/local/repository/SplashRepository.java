/**
 * Created by Kalyter on 2016/10/28.
 */
package cn.kalyter.ss.data.local.repository;

import android.content.Context;
import android.content.SharedPreferences;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.data.local.SplashSource;

import static cn.kalyter.ss.config.Config.SP;


public class SplashRepository implements SplashSource {
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private static final String KEY_IS_LOGIN = "IS_LOGIN";
    private static final String KEY_SPLASH_LAST_TIME = "SPLASH_LAST_TIME";

    public SplashRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(SP, Context.MODE_PRIVATE);
    }
    @Override
    public Boolean getIsLogin() {
        return mSharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    @Override
    public Long getSplashLastTime() {
        return mSharedPreferences.getLong(KEY_SPLASH_LAST_TIME, 0);
    }

    @Override
    public String getClientId() {
        return mSharedPreferences.getString(Config.KEY_CLIENT_ID, "");
    }

    @Override
    public void updateIsLogin(Boolean isLogin) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.apply();
    }

    @Override
    public void updateSplashLastTime(Long time) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(KEY_SPLASH_LAST_TIME, time);
        editor.apply();
    }
}
