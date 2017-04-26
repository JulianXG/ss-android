/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.dagger.module;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.data.remote.api.UploadService;
import cn.kalyter.ss.data.remote.api.UserService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule {
    private static final String TAG = "ApiModule";
    private static final String API_BASE_URL = "http://ss.kalyter.cn/";
//    private static final String API_BASE_URL = "http://192.168.1.135:8080";
//    private static final String API_BASE_URL = "http://172.24.134.3:8080";

    @Provides
    HttpLoggingInterceptor provideLogging() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(TAG, message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    OkHttpClient provideClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        ObjectMapper mapper = new ObjectMapper();
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    UserService provideLogin(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    MicroblogService provideMicroblog(Retrofit retrofit) {
        return retrofit.create(MicroblogService.class);
    }

    @Provides
    UploadService provideUpload(Retrofit retrofit) {
        return retrofit.create(UploadService.class);
    }

    @Provides
    OperateService provideOperate(Retrofit retrofit) {
        return retrofit.create(OperateService.class);
    }
}
