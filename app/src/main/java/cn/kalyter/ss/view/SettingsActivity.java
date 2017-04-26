package cn.kalyter.ss.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

import javax.inject.Inject;

import butterknife.BindView;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.SettingsContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerSettingsComponent;
import cn.kalyter.ss.dagger.module.SettingsModule;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class SettingsActivity extends BaseActivity implements SettingsContract.View {

    @Inject
    SettingsContract.Presenter mPresenter;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.change_password)
    LSettingItem mChangePassword;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.settings);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.start();
        mChangePassword.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                final EditText editText = new EditText(SettingsActivity.this);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("验证原密码")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(editText.getText())) {
                                    Toast.makeText(SettingsActivity.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    String originPassword = editText.getText().toString();
                                    mPresenter.validateOriginPassword(originPassword);
                                }
                            }
                        }).show();
            }
        });
    }

    @Override
    protected void injectComponent() {
        DaggerSettingsComponent.builder()
                .settingsModule(new SettingsModule(this))
                .apiComponent(App.getApiComponent())
                .repositoryComponent(App.getRepositoryComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void showValidating() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.validating));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showValidateOriginError() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "原密码有误，请重新输入", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangePassword() {
        Toast.makeText(this, "认证成功", Toast.LENGTH_SHORT).show();
        mProgressDialog.dismiss();
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }
}
