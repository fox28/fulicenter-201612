package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.dao.UserDao;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.SharePreferenceUtils;
import com.example.apple.fulicenter.ui.view.MFGT;

/**
 * Created by apple on 2017/3/26.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    int time = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String username = SharePreferenceUtils.getInstance().getUsername();
                if (username != null) {
                    User user = UserDao.getInstance(SplashActivity.this).getUserInfo(username);
                    L.e(TAG, "onStart, user = "+user);
                    FuLiCenterApplication.setCurrentUser(user);
                }
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                // 跟下面作用相同
                MFGT.gotoMainActivity(SplashActivity.this);
                SplashActivity.this.finish();

            }
        }, time);
    }
}
