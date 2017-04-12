package com.example.apple.fulicenter.application;

import android.app.Application;
import android.content.Context;

import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.dao.UserDao;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.SharePreferenceUtils;

/**
 * Created by apple on 2017/3/27.
 */

public class FuLiCenterApplication extends Application {

    static FuLiCenterApplication instance;
    static User currentUser;

    public static User getCurrentUser() {
        if (currentUser == null) {
            final String username = SharePreferenceUtils.getInstance().getUsername();
            L.e("application", "username = " +username);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = UserDao.getInstance(instance).getUserInfo(username);
                }
            }).start(); // 别忘了start()!
        }
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        FuLiCenterApplication.currentUser = currentUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
