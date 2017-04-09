/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.contract;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import cn.kalyter.ss.common.BasePresenter;
import cn.kalyter.ss.common.BaseView;
import cn.kalyter.ss.model.Microblog;

public interface NewContract {
    interface View extends BaseView {
        void showValidError();

        String getContent();

        void showChoice();

        void showSelectPhoto();

        void showTakePhoto();

        void showPhoto(String filename);

        void cancelPhoto(int index);

        List<String> getImages();

        void showRequestPermissionError();

        void showIOError();

        void showEditPhoto(String filename);

        void showPosting();

        void showPostSuccess();

        void showPostFail();

        void showMicroblog(Microblog microblog);

        long getRepostMicroblogId();
    }

    interface Presenter extends BasePresenter {
        boolean requirePermissions(Activity activity);

        void handleTakePhoto(String path, Bitmap bitmap);

        void postMicroblog();

        void repostMicroblog();

        void loadMicroblog(long microblogId);
    }
}
