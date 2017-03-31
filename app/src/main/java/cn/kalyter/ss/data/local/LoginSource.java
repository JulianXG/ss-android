/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.data.local;


import cn.kalyter.ss.model.User;

public interface LoginSource {
    void saveUser(User user);

    User getUser();
//
//    void saveToken(Token token);
//
//    Token getToken();
}
