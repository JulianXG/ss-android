package cn.kalyter.ss.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.NewContract;
import cn.kalyter.ss.data.local.UserSource;
import cn.kalyter.ss.data.remote.api.MicroblogService;
import cn.kalyter.ss.data.remote.api.OperateService;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.model.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.kalyter.ss.config.Config.LOCAL_DEVICE;
import static cn.kalyter.ss.config.Config.REQUEST_CODE_PERMISSION;

/**
 * Created by Kalyter on 2017-4-4 0004.
 */

public class NewPresenter implements NewContract.Presenter {
    private Context mContext;
    private NewContract.View mView;
    private MicroblogService mMicroblogService;
    private UserSource mUserSource;
    private OperateService mOperateService;

    private final String[] REQUEST_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public NewPresenter(Context context,
                        NewContract.View view,
                        MicroblogService microblogService,
                        UserSource userSource,
                        OperateService operateService) {
        mContext = context;
        mView = view;
        mMicroblogService = microblogService;
        mUserSource = userSource;
        mOperateService = operateService;
    }

    @Override
    public void start() {

    }

    /**
     * 获取当前情况下是否拥有相应权限，如果拥有则返回true，否则就主动申请权限，并返回false
     * @param activity Activity
     * @return 是否拥有相应的权限
     */
    @Override
    public boolean requirePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> deniedPermissions = new ArrayList<>();
            for (String permission : REQUEST_PERMISSIONS) {
                int current = ContextCompat.checkSelfPermission(
                        activity, permission);
                if (current == PackageManager.PERMISSION_DENIED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions.size() > 0) {
                ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[deniedPermissions.size()]),
                        REQUEST_CODE_PERMISSION);
                return false;
            }
        }
        return true;
    }

    /**
     * 保存图片
     * @param path 文件存储路径
     * @param bitmap 图片
     */
    @Override
    public void handleTakePhoto(String path, Bitmap bitmap) {
        String filename = path + new Date().getTime() + ".jpg";
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(filename));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            mView.showEditPhoto(filename);
        } catch (IOException e) {
            e.printStackTrace();
            mView.showIOError();
        }
    }

    @Override
    public void postMicroblog() {
        mView.showPosting();
        Microblog microblog = new Microblog();
        microblog.setUserId(mUserSource.getUserId());
        microblog.setContent(mView.getContent());
        List<String> images = mView.getImages();
        microblog.setDeviceName(LOCAL_DEVICE);
        microblog.setPostTime(new Date().getTime());
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (String path : images) {
            File imageFile = new File(path);
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", imageFile.getName(), body);
            imageParts.add(part);
        }
        mMicroblogService.postMicroblog(microblog, imageParts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.showPostSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showPostFail();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void repostMicroblog() {
        mView.showPosting();
        Microblog microblog = new Microblog();
        microblog.setUserId(mUserSource.getUserId());
        microblog.setContent(mView.getContent());
        microblog.setDeviceName(Config.LOCAL_DEVICE);
        microblog.setPostTime(new Date().getTime());
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (String path : mView.getImages()) {
            File imageFile = new File(path);
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", imageFile.getName(), body);
            imageParts.add(part);
        }
        mOperateService.repostMicroblog(mView.getRepostMicroblogId(),
                microblog, imageParts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        mView.showPostSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showPostFail();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    @Override
    public void loadMicroblog(long microblogId) {
        mMicroblogService.getMicroblog(microblogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Microblog>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<Microblog> microblogResponse) {
                        mView.showMicroblog(microblogResponse.getData());
                    }
                });
    }
}
