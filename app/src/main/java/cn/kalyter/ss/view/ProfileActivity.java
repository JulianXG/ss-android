package cn.kalyter.ss.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.ProfileContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerProfileComponent;
import cn.kalyter.ss.dagger.module.ProfileModule;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.GlideCircleTransform;
import cn.kalyter.ss.util.Util;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    @Inject
    ProfileContract.Presenter mPresenter;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar_image)
    ImageView mAvatarImage;
    @BindView(R.id.avatar_container)
    LinearLayout mAvatarContainer;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.nickname_container)
    LinearLayout mNicknameContainer;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.username_container)
    LinearLayout mUsernameContainer;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.gender_container)
    LinearLayout mGenderContainer;
    @BindView(R.id.school)
    TextView mSchool;
    @BindView(R.id.school_container)
    LinearLayout mSchoolContainer;
    @BindView(R.id.tel)
    TextView mTel;
    @BindView(R.id.tel_container)
    LinearLayout mTelContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.profile);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.start();
    }

    @Override
    protected void injectComponent() {
        DaggerProfileComponent.builder()
                .profileModule(new ProfileModule(this))
                .apiComponent(App.getApiComponent())
                .repositoryComponent(App.getRepositoryComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .into(mAvatarImage);
        mNickname.setText(user.getNickname());
        mUsername.setText(user.getUsername());
        mSchool.setText(user.getSchool());
        mGender.setText(Util.parseSex(user.getSex()));
        mTel.setText(user.getTel());
    }
}
