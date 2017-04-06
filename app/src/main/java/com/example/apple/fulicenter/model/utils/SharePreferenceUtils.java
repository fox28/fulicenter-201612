package com.example.apple.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.apple.fulicenter.application.FuLiCenterApplication;

/**
 * Created by apple on 2017/4/6.
 */

/**
 * 一般在LoginActivity的loginSuccess()中调用
 */
public class SharePreferenceUtils {
    // sharePreference的文件名, 该文件用于保存username、username可以用于从database读取数据
    private static final String SHARE_PREFERENCE_NAME = "cn.kkk.fulicenter_save_userinfo";
    // 保存数据的字段
    private static final String SAVE_USERINFO_USERNAME = "m_user_username";

    static SharePreferenceUtils instance;
    SharedPreferences sharePreferences;
    SharedPreferences.Editor editor;

    public SharePreferenceUtils() {
        sharePreferences = FuLiCenterApplication.getInstance()
                .getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharePreferences.edit();
    }

    public static SharePreferenceUtils getInstance() {
        if (instance == null) {
            instance = new SharePreferenceUtils();
        }
        return instance;
    }

    // 保存数据方法
    // 切记别忘了接commit()
    public void setUsername(String username) {
        editor.putString(SAVE_USERINFO_USERNAME, username).commit();
    }

    // 读取数据方法
    public String getUsername() {
        return sharePreferences.getString(SAVE_USERINFO_USERNAME, null);
    }

    // 用户退出登录后、清空数据
    public void removeUsername() {
        editor.remove(SAVE_USERINFO_USERNAME).commit();
    }
}
