/**
 * Created by Kalyter on 2016/10/25.
 */
package cn.kalyter.ss.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.LoginContract;
import cn.kalyter.ss.dagger.App;
import cn.kalyter.ss.dagger.component.DaggerLoginComponent;
import cn.kalyter.ss.dagger.module.LoginModule;
import cn.kalyter.ss.model.LoginUser;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.email)
    EditText mEmail;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.login)
    AppCompatButton mLogin;
    @BindView(R.id.sign_up)
    TextView mSignUp;
    @BindView(R.id.snackbar_container)
    CoordinatorLayout mSnackbarContainer;

    @Inject
    LoginContract.Presenter mPresenter;
    @Inject
    ProgressDialog mProgressDialog;

    @OnClick(R.id.login)
    void login() {
        if (TextUtils.isEmpty(mEmail.getText()) ||
                TextUtils.isEmpty(mPassword.getText())) {
            showValidateError();
        } else {
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(mEmail.getText().toString());
            loginUser.setPassword(mPassword.getText().toString());
            mPresenter.login(loginUser);
        }
    }

    @OnClick(R.id.sign_up)
    void showSignUp() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginStart() {
        mProgressDialog.setProgressStyle(R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setMessage(getString(R.string.hint_authenticating));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    @Override
    public void showLoginFailed() {
        mProgressDialog.hide();
        Log.e(TAG, "showLoginFailed: 用户名或密码错误");
        Snackbar.make(mSnackbarContainer,
                R.string.error_username_or_password, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidateError(String message) {
        Snackbar.make(mSnackbarContainer,
                message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideSoftInputMethod() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showMainFrame() {
        startActivity(new Intent(LoginActivity.this, MainFrameActivity.class));
        finish();
    }

    @Override
    public void closeLoginLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showValidateError() {
        Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void injectComponent() {
        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this, LoginActivity.this))
                .appComponent(App.getAppComponent())
                .apiComponent(App.getApiComponent())
                .repositoryComponent(App.getRepositoryComponent())
                .build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}
