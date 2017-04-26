package cn.kalyter.ss.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.RegisterContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerRegisterComponent;
import cn.kalyter.ss.dagger.module.RegisterModule;
import cn.kalyter.ss.model.User;

/**
 * Created by Kalyter on 2017-4-16 0016.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.password_confirm)
    EditText mPasswordConfirm;

    @Inject
    RegisterContract.Presenter mPresenter;
    @Inject
    ProgressDialog mProgressDialog;
    @BindView(R.id.nickname)
    EditText mNickname;
    @BindView(R.id.register)
    AppCompatButton mRegister;
    @BindView(R.id.username_container)
    TextInputLayout mUsernameContainer;
    @BindView(R.id.password_confirm_container)
    TextInputLayout mPasswordConfirmContainer;

    @OnClick(R.id.register)
    void register() {
        if (TextUtils.isEmpty(mUsername.getText()) ||
                TextUtils.isEmpty(mPassword.getText()) ||
                TextUtils.isEmpty(mPasswordConfirm.getText())) {
            showValidateError();
            return;
        }

        if (!mUsernameContainer.isErrorEnabled() &&
                !mPasswordConfirmContainer.isErrorEnabled()) {
            User user = new User();
            user.setUsername(mUsername.getText().toString());
            user.setPassword(mPassword.getText().toString());
            user.setNickname(mNickname.getText().toString());
            mPresenter.register(user);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle.setText(R.string.register);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.validateUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    showPasswordConfirmError();
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
        DaggerRegisterComponent.builder()
                .apiComponent(App.getApiComponent())
                .registerModule(new RegisterModule(this, this))
                .build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void showRegistering() {
        mProgressDialog.setMessage(getString(R.string.registering));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void showRegisterSuccess() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "注册用户成功，请登录", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showRegisterError() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "用户注册失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showValidateError() {
        Toast.makeText(this, "表单数据输入有误，请检查后重新注册", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordConfirmError() {
        mPasswordConfirmContainer.setError(getString(R.string.confirm_password_error));
    }

    @Override
    public void showUsernameConflict() {
        mProgressDialog.dismiss();
        mUsernameContainer.setErrorEnabled(true);
        mUsernameContainer.setError("用户名已存在");
    }

    @Override
    public void showUsernameAvailable() {
        mUsernameContainer.setErrorEnabled(false);
    }
}
