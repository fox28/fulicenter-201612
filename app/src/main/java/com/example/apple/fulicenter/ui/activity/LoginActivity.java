package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/4.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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

    }

    @OnClick(R.id.backClickArea)
    public void onBackClicked() {
        MFGT.finish(this);
    }
}
