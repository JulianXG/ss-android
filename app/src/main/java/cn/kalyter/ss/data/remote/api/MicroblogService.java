/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.data.remote.api;

import javax.ws.rs.core.Response;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface MicroblogService {
    @POST("/microblog")
    Observable<Response> postMicroblog();

    @GET("/microblog")
    Observable<Response> postMicroblogPagination();
}
