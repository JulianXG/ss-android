/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.data.local.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.Util;


public class UserRepository implements UserSource {
    private static final String TAG = "UserRepository";

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private static final String KEY_USER_ID = "USER_ID";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_NICKNAME = "NICKNAME";
    private static final String KEY_REAL_NAME = "REAL_NAME";
    private static final String KEY_SEX = "SEX";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_QQ = "QQ";
    private static final String KEY_SCHOOL = "SCHOOL";
    private static final String KEY_TEL = "TEL";
    private static final String KEY_MICROBLOG_COUNT = "MICROBLOG_COUNT";
    private static final String KEY_LOCATION = "LOCATION";
    private static final String KEY_TAG = "TAG";
    private static final String KEY_LOGIN_TIME = "LOGIN_TIME";
    private static final String KEY_LOGIN_COUNT = "LOGIN_COUNT";
    private static final String KEY_LAST_LOGIN_TIME = "LAST_LOGIN_TIME";
    private static final String KEY_AVATAR = "AVATAR";
    private static final String KEY_ROLE_ID = "ROLE_ID";

    public UserRepository(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Config.SP, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUser(User user) {
        user = Util.handleNull(user);
        if (user != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(KEY_USER_ID, user.getId());
            editor.putString(KEY_USERNAME, user.getUsername());
            editor.putString(KEY_NICKNAME, user.getNickname());
            editor.putString(KEY_AVATAR, user.getAvatar());
            editor.putString(KEY_REAL_NAME, user.getRealName());
            editor.putInt(KEY_SEX, user.getSex());
            editor.putString(KEY_EMAIL, user.getEmail());
            editor.putString(KEY_QQ, user.getQq());
            editor.putString(KEY_SCHOOL, user.getSchool());
            editor.putString(KEY_TEL, user.getTel());
            editor.putInt(KEY_MICROBLOG_COUNT, user.getMicroblogCount());
            editor.putString(KEY_LOCATION, user.getLocation());
            editor.putString(KEY_TAG, user.getTag());
            editor.putLong(KEY_LOGIN_TIME, user.getLoginTime().getTime());
            editor.putInt(KEY_LOGIN_COUNT, user.getLoginCount());
            editor.putLong(KEY_LAST_LOGIN_TIME, user.getLastLoginTime().getTime());
            editor.putInt(KEY_ROLE_ID, user.getRoleId());
            editor.apply();
        }
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setId(mSharedPreferences.getInt(KEY_USER_ID, 0));
        user.setUsername(mSharedPreferences.getString(KEY_USERNAME, ""));
        user.setNickname(mSharedPreferences.getString(KEY_NICKNAME, ""));
        user.setAvatar(mSharedPreferences.getString(KEY_AVATAR, ""));
        user.setRealName(mSharedPreferences.getString(KEY_REAL_NAME, ""));
        user.setSex((byte) mSharedPreferences.getInt(KEY_SEX, -1));
        user.setEmail(mSharedPreferences.getString(KEY_EMAIL, ""));
        user.setQq(mSharedPreferences.getString(KEY_QQ, ""));
        user.setSchool(mSharedPreferences.getString(KEY_SCHOOL, ""));
        user.setTel(mSharedPreferences.getString(KEY_TEL, ""));
        user.setMicroblogCount(mSharedPreferences.getInt(KEY_MICROBLOG_COUNT, 0));
        user.setLocation(mSharedPreferences.getString(KEY_LOCATION, ""));
        user.setTag(mSharedPreferences.getString(KEY_TAG, ""));
        long loginTime = mSharedPreferences.getLong(KEY_LOGIN_TIME, 0);
        user.setLoginTime(new Date(loginTime));
        user.setLoginCount(mSharedPreferences.getInt(KEY_LOGIN_COUNT, 0));
        long lastLoginTime = mSharedPreferences.getLong(KEY_LAST_LOGIN_TIME, 0);
        user.setLastLoginTime(new Date(lastLoginTime));
        user.setRoleId(mSharedPreferences.getInt(KEY_ROLE_ID, 0));
        return user;
    }

    @Override
    public int getUserId() {
        return mSharedPreferences.getInt(KEY_USER_ID, 0);
    }
//
//    @Override
//    public void saveToken(Token token) {
//        token.setCreateTime(new Date());
//        token.setUpdateTime(new Date());
//        token.setIsDeleted(false);
//        token.save();
//    }
}
