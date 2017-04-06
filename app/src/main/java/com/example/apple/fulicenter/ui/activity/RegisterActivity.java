package com.example.apple.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.tv_common_title)
    TextView mtvCommonTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_nick)
    EditText etNick;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;

    String username;
    String nick;
    String password;
    IUserModel model;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        model = new UserModel();
        initView();
    }

    private void initView() {
        mtvCommonTitle.setText(R.string.register_title);
    }

    @OnClick({R.id.backClickArea, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(RegisterActivity.this);
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        if (checkInput()) {
            showDialog();
            model.register(this, username, nick, MD5.getMessageDigest(password),
                    new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String result_t) {
                    Result result = ResultUtils.getResultFromJson(result_t, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            registerSuccess();
                        } else if (result.getRetCode() == I.MSG_REGISTER_USERNAME_EXISTS) {
                            CommonUtils.showShortToast(R.string.register_fail_exists);
                        } else {
                            CommonUtils.showShortToast(R.string.register_fail);
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(R.string.register_fail);
                    dialog.dismiss();
                }
            });
        }
    }

    private void registerSuccess() {
        setResult(RESULT_OK, new Intent().putExtra(I.User.USER_NAME, username));
        CommonUtils.showShortToast(getString(R.string.register_success));
        MFGT.finish(RegisterActivity.this);
    }

    private void showDialog() {
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage(getString(R.string.registering));
        dialog.show();
    }

    // 注意别忘了加trim()!!
    private boolean checkInput() {
        username = etUsername.getText().toString().trim();
        nick = etNick.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            etUsername.requestFocus();
            etUsername.setError(getString(R.string.user_name_connot_be_empty));
            return false;
        }
        // 注意前面的'!'
        if (! username.matches("[a-zA-Z]\\w{5,15}")) {
            etUsername.requestFocus();
            etUsername.setError(getString(R.string.illegal_user_name));
            return false;
        }
        if (TextUtils.isEmpty(nick)) {
            etNick.requestFocus();
            etNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.password_connot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(password2)) {
            etPassword2.requestFocus();
            etPassword2.setError(getString(R.string.confirm_password_connot_be_empty));
            return false;
        }
        if (!password.equals(password2)) {
            etPassword2.requestFocus();
            etPassword2.setError(getString(R.string.two_input_password));
            return false;
        }
        return true;
    }
}
