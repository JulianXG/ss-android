package cn.kalyter.ss.view;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.ChangePasswordContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerChangePasswordComponent;
import cn.kalyter.ss.dagger.module.ChangePasswordModule;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {
    @Inject
    ChangePasswordContract.Presenter mPresenter;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.password_confirm)
    EditText mPasswordConfirm;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.password_confirm_container)
    TextInputLayout mPasswordConfirmContainer;

    private ProgressDialog mProgressDialog;

    @OnClick(R.id.confirm)
    void changePassword() {
        if (TextUtils.isEmpty(mPassword.getText()) ||
                TextUtils.isEmpty(mPasswordConfirm.getText())) {
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.changePassword(mPassword.getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();
        mTitle.setText(R.string.change_password);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mPassword.getText().toString().equals(s.toString())) {
                    mPasswordConfirmContainer.setErrorEnabled(true);
                    mPasswordConfirmContainer.setError(getString(R.string.confirm_password_error));
                } else {
                    mPasswordConfirmContainer.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void injectComponent() {
        DaggerChangePasswordComponent.builder()
                .changePasswordModule(new ChangePasswordModule(this))
                .repositoryComponent(App.getRepositoryComponent())
                .apiComponent(App.getApiComponent())
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void showChanging() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(getString(R.string.submitting));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showChangeSuccess() {
        Toast.makeText(this, "修改密码成功，请重新登录", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangeError() {
        Toast.makeText(this, "修改密码失败，请重试！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        startActivity(mainIntent);
    }
}
