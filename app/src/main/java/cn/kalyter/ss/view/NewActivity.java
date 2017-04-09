/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.contract.NewContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerNewComponent;
import cn.kalyter.ss.dagger.module.NewModule;
import cn.kalyter.ss.model.Microblog;
import cn.kalyter.ss.util.Util;

import static cn.kalyter.ss.config.Config.FROM_ALBUM;
import static cn.kalyter.ss.config.Config.FROM_CAPTURE;
import static cn.kalyter.ss.config.Config.REQUEST_CODE_EDIT_PHOTO;
import static cn.kalyter.ss.config.Config.REQUEST_CODE_PERMISSION;
import static cn.kalyter.ss.config.Config.REQUEST_CODE_SELECT_PHOTO;
import static cn.kalyter.ss.config.Config.REQUEST_CODE_TAKE_PHOTO;

public class NewActivity extends BaseActivity implements NewContract.View {
    @Inject
    NewContract.Presenter mPresenter;
    @BindView(R.id.microblog_content)
    EditText mMicroblogContent;
    @BindView(R.id.add_photo)
    ImageView mAddPhoto;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.image_container)
    LinearLayout mImageContainer;
    @BindView(R.id.repost_microblog_container)
    LinearLayout mRepostMicroblogContainer;

    private String path;
    private List<String> images = new ArrayList<>(3);
    private int mPostType  = Config.TYPE_POST;
    private long mRepostMicroblogId;

    @Inject
    ProgressDialog mProgressDialog;

    @Inject
    ImgSelConfig mConfig;

    @OnClick(R.id.add_photo)
    void choice() {
        showChoice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TAKE_PHOTO:
                    // 拍照
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPresenter.handleTakePhoto(path, photo);
                    break;
                case REQUEST_CODE_EDIT_PHOTO:
                    // 编辑照片
                    boolean isImageEdit = data.getBooleanExtra(EditImageActivity.IMAGE_IS_EDIT, false);
                    if (isImageEdit) {
                        String filename = data.getStringExtra(EditImageActivity.EXTRA_OUTPUT);
                        showPhoto(filename);
                    }
                    break;
                case REQUEST_CODE_SELECT_PHOTO:
                    List<String> result = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                    for (String image : result) {
                        showPhoto(image);
                    }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        showRequestPermissionError();
                        return;
                    }
                }
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getExternalFilesDir("").getPath();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.inflateMenu(R.menu.submit);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.commit:
                        if (TextUtils.isEmpty(mMicroblogContent.getText())
                                || getImages().size() == 0) {
                            showValidError();
                        } else {
                            if (mPostType == Config.TYPE_REPOST) {
                                mPresenter.repostMicroblog();
                            } else if (mPostType == Config.TYPE_POST) {
                                mPresenter.postMicroblog();
                            }
                        }
                        break;
                }
                return false;
            }
        });

        long microblogId = getIntent().getLongExtra(Config.KEY_MICROBLOG_ID, 0);
        if (microblogId > 0) {
            // 转发
            mTitle.setText(R.string.repost);
            mRepostMicroblogId = microblogId;
            mPresenter.loadMicroblog(microblogId);
            mPostType = Config.TYPE_REPOST;
        }
    }

    @Override
    protected void injectComponent() {
        DaggerNewComponent.builder()
                .apiComponent(App.getApiComponent())
                .repositoryComponent(App.getRepositoryComponent())
                .newModule(new NewModule(this, this))
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new;
    }

    @Override
    public void showValidError() {
        Toast.makeText(this, "请说些什么，再贴几张图片后再发送吧！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getContent() {
        return mMicroblogContent.getText().toString();
    }

    @Override
    public void showChoice() {
        new AlertDialog.Builder(this)
                .setTitle("请选择图片来源")
                .setItems(R.array.picture_sources, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case FROM_ALBUM:
                                showSelectPhoto();
                                break;
                            case FROM_CAPTURE:
                                showTakePhoto();
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void showSelectPhoto() {
        ImgSelActivity.startActivity(this, mConfig, REQUEST_CODE_SELECT_PHOTO);
    }

    @Override
    public void showTakePhoto() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_TAKE_PHOTO);
    }

    @Override
    public void showPhoto(final String filename) {
        View imageView = getLayoutInflater().inflate(R.layout.image_item, mImageContainer, false);
        final int index = images.size();
        images.add(filename);
        ImageView image = (ImageView) imageView.findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditPhoto(filename);
            }
        });
        Glide.with(this)
                .load(filename)
                .centerCrop()
                .into(image);
        ImageView cancel = (ImageView) imageView.findViewById(R.id.cancel_image);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPhoto(index);
            }
        });
        mImageContainer.addView(imageView, index);
    }

    @Override
    public void cancelPhoto(int index) {
        images.remove(index);
        mImageContainer.removeViewAt(index);
    }

    @Override
    public List<String> getImages() {
        return images;
    }

    @Override
    public void showRequestPermissionError() {
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showIOError() {
        Toast.makeText(this, "进行文件读取操作时出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditPhoto(String filename) {
        EditImageActivity.start(this, filename, path, REQUEST_CODE_EDIT_PHOTO);
    }

    @Override
    public void showPosting() {
        mProgressDialog.setMessage("正在发送……");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showPostSuccess() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showPostFail() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "发送分享失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMicroblog(Microblog microblog) {
        Util.showRepostMicroblog(this, mRepostMicroblogContainer, microblog);
    }

    @Override
    public long getRepostMicroblogId() {
        return mRepostMicroblogId;
    }
}
