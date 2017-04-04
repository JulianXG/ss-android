package cn.kalyter.ss.data.remote.api;

import java.util.List;

import cn.kalyter.ss.model.Comment;
import cn.kalyter.ss.model.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-3 0003.
 */

public interface OperateService {
    @PUT("/operate/like/{userId}/{microblogId}")
    Observable<Response> likeMicroblog(@Path("userId") long userId,
                                       @Path("microblogId") long microblogId);

    @PUT("/operate/cancel/{userId}/{microblogId}")
    Observable<Response> cancelLike(@Path("userId") long userId,
                                    @Path("microblogId") long microblogId);

    @POST("/operate/comments")
    Observable<Response> postComment(@Body Comment comment);

    @GET("/operate/comments/{microblogId}")
    Observable<Response<List<Comment>>> getCommentsById(@Path("microblogId") long microblogId);
}
