/**
 * Created by Kalyter on 2016/11/7.
 */
package cn.kalyter.ss.data.local.repository;

import java.util.Date;

import cn.kalyter.ss.data.local.LoginSource;
import cn.kalyter.ss.model.User;

public class LoginRepository implements LoginSource {
//    @Override
//    public Token getToken() {
//        return Select.from(Token.class)
//                .where(Condition.prop("is_deleted").eq(false))
//                .orderBy("id")
//                .first();
//    }

    @Override
    public void saveUser(User user) {
        Date now = new Date();
//        user.setIsDeleted(false);
//        user.setCreateTime(now);
//        user.setUpdateTime(now);
//        user.save();
    }

    @Override
    public User getUser() {
        return null;
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
