/**
 * Created by Kalyter on 2016/10/28.
 */
package cn.kalyter.ss.data.remote.api;

import cn.kalyter.ss.model.LoginUser;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UserService {
    @POST("/user/login")
    Observable<Response<User>> login(@Body LoginUser loginUser);
}
