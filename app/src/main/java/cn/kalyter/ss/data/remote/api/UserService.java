/**
 * Created by Kalyter on 2016/10/28.
 */
package cn.kalyter.ss.data.remote.api;

import cn.kalyter.ss.model.LoginUser;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserService {
    @POST("/login")
    Observable<Response<User>> login(@Body LoginUser loginUser);

    @POST("/user")
    Observable<Response<User>> register(@Body User user);

    @GET("/user/{id}")
    Observable<Response<User>> getUserById(@Path("id") int id);
}
