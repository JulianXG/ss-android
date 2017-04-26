package cn.kalyter.ss.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leon.lib.settingview.LSettingItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseFragment;
import cn.kalyter.ss.contract.MeContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerMeComponent;
import cn.kalyter.ss.dagger.module.MeModule;
import cn.kalyter.ss.model.User;
import cn.kalyter.ss.util.GlideCircleTransform;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class MeFragment extends BaseFragment implements MeContract.View {


    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.membership_point)
    TextView mMembershipPoint;
    @BindView(R.id.profile_container)
    LinearLayout mProfileContainer;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.profile)
    LSettingItem mProfile;
    @BindView(R.id.microblog_center)
    LSettingItem mMicroblogCenter;
    @BindView(R.id.settings)
    LSettingItem mSettings;
    @BindView(R.id.about)
    LSettingItem mAbout;

    @OnClick(R.id.logout)
    void logout() {
        new AlertDialog.Builder(getContext())
                .setTitle("确认退出登录？")
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.logout();
                    }
                }).setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Inject
    MeContract.Presenter mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.start();
        mProfile.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        mSettings.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });

        mAbout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });

        mMicroblogCenter.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                startActivity(new Intent(getContext(), MicroblogCenterActivity.class));
            }
        });
    }

    @Override
    protected void injectComponent() {
        DaggerMeComponent.builder()
                .meModule(new MeModule(this, getContext()))
                .apiComponent(App.getApiComponent())
                .repositoryComponent(App.getRepositoryComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void showAdmin() {
        mMicroblogCenter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormalUser() {
        mMicroblogCenter.setVisibility(View.GONE);
    }

    @Override
    public void showUser(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_person_outline_black_24dp)
                .transform(new GlideCircleTransform(getContext()))
                .into(mAvatar);

        mUsername.setText(user.getNickname());
    }

    @Override
    public void showLogin() {
        Toast.makeText(getContext(), "退出登录成功！请重新登录", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}
