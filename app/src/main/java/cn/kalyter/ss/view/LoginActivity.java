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
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


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

//    @NotEmpty(message = "输入用户名")
    @BindView(R.id.email)
    EditText mEmail;

//    @Password(min = 0, message = "无效的密码")
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
//    @Inject
//    Validator mValidator;

    @OnClick(R.id.login)
    void login() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(mEmail.getText().toString());
        loginUser.setPassword(mPassword.getText().toString());
        mPresenter.login(loginUser);
//        mValidator.validate();
    }

    @OnClick(R.id.sign_up)
    void showSignUp() {
//        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mValidator.setValidationListener(new Validator.ValidationListener() {
//            @Override
//            public void onValidationSucceeded() {
//                LoginUser loginUser = new LoginUser();
//                loginUser.setUsername(mEmail.getText().toString());
//                loginUser.setPassword(mPassword.getText().toString());
//                mPresenter.login(loginUser);
//            }
//
//            @Override
//            public void onValidationFailed(List<ValidationError> errors) {
//                for (ValidationError error : errors) {
//                    View view = error.getView();
//                    String message = error.getCollatedErrorMessage(getApplicationContext());
//
//                    // Display error messages ;)
//                    if (view instanceof EditText) {
//                        ((EditText) view).setError(message);
//                    } else {
//                        showValidateError(message);
//                    }
//                }
//            }
//        });
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
