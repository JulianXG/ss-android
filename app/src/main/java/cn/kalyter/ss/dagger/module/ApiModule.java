/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.dagger.module;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.kalyter.ss.data.remote.api.UserService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    private static final String TAG = "ApiModule";
    private static final String API_BASE_URL = "http:/ss.kalyter.cn/api/";
//    private static final String API_BASE_URL = "http:/192.168.155.1:8080";

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

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
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    UserService provideLogin(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }
}
