/**
 * Created by Kalyter on 2016/10/28.
 */
package cn.kalyter.ss.data.local;

public interface SplashSource {
    void updateIsLogin(Boolean isLogin);

    void updateSplashLastTime(Long time);

    Boolean getIsLogin();

    Long getSplashLastTime();

    String getClientId();
}
