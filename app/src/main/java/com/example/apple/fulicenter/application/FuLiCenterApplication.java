package com.example.apple.fulicenter.application;

import android.app.Application;
import android.content.Context;

import com.example.apple.fulicenter.model.bean.User;

/**
 * Created by apple on 2017/3/27.
 */

public class FuLiCenterApplication extends Application {

    static FuLiCenterApplication instance;
    static User currentUser;

    public static User getCurrentUser() {
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
