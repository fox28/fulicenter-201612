package com.example.apple.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.Result;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.IUserModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.net.UserModel;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.MD5;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/4.
 */
public class LoginActivity extends AppCompatActivity {

    ProgressDialog dialog;
    String username;
    String password;
    IUserModel model;


    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        model = new UserModel();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                MFGT.gotoRegisterActivity(LoginActivity.this);
                break;
        }
    }

    private void login() {
        if (checkInput()) {
            showDialog();
            model.login(LoginActivity.this, username, MD5.getMessageDigest(password),
                    new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String result_t) {
                    Result result = ResultUtils.getResultFromJson(result_t, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user = (User) result.getRetData();
                            if (user != null) {
                                loginSuccess(user);
                            }
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showShortToast(R.string.login_fail_unknow_user);
                            }
                               if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showShortToast(R.string.login_fail_error_password);
                            }
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(R.string.login_fail);
                    dialog.dismiss();
                }
            });
        }

    }

    private void loginSuccess(User user) {
        FuLiCenterApplication.setCurrentUser(user);
    }

    private boolean checkInput() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mEtUsername.requestFocus();
            mEtUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.requestFocus();
            mEtPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (username.matches("[a-zA-Z]\\W{5,15}")) {
            mEtUsername.requestFocus();
            mEtUsername.setError(getString(R.string.illegal_user_name));
            return false;
        }
        return true;
    }

    private void showDialog() {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.registering));
        dialog.show();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClicked() {
        MFGT.finish(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String username = data.getStringExtra(I.User.USER_NAME);
            mEtUsername.setText(username);
        }
    }
}
