package cn.kalyter.ss.data.remote.api;

import cn.kalyter.ss.model.Response;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Kalyter on 2017-3-31 0031.
 */

public interface UploadService {
    @Multipart
    @POST("/upload")
    Observable<Response> uploadFile(@Part MultipartBody.Part file);
}
