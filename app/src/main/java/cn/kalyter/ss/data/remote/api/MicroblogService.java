/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.data.remote.api;

import java.util.List;

import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.Response;
import cn.kalyter.ss.model.Search;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface MicroblogService {
    @Multipart
    @POST("/user/microblog")
    Observable<Response> postMicroblog(@Part("microblog") Microblog microblog,
                                       @Part List<MultipartBody.Part> image);

    @GET("/user/microblog/trends/{userId}/{pageSize}/{from}")
    Observable<Response<List<Microblog>>> getTrends(@Path("userId")long userId,
                                                    @Path("pageSize") int pageSize,
                                                    @Path("from") long microblogId);

    @GET("/user/microblog/page/{page}/{pageSize}")
    Observable<Response<List<Microblog>>> postMicroblogPagination(@Body Search search, @Path("page") int page,
                                                                 @Path("pageSize")int pageSize);

    @DELETE("/user/microblog/{id}")
    Observable<Response> deleteMicroblogById(@Path("id") int id);

    @GET("/user/microblog/{id}")
    Observable<Response<Microblog>> getMicroblog(@Path("id") long microblogId);
}
