package com.example.apple.fulicenter.model.dao;

import android.content.Context;

import com.example.apple.fulicenter.model.bean.User;

/**
 * Created by apple on 2017/4/6.
 */

public class UserDao {
    public static final String USER_TABLE_NAME = "t_fulicenter_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_suffix";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_UPDATE_TIME = "m_user_avatar_update_time";

    private static UserDao instance;

    public static UserDao getInstance(Context context) {
        if (instance == null) {
            instance = new UserDao(context);
        }
        return instance;
    }

    public UserDao(Context context) {
        // 调用DBManager的initDB()方法创建数据库
        DBManager.getInstance().initDB(context);
    }

    public boolean saveUserInfo(User user) {
        return DBManager.getInstance().saveUserInfo(user);
    }

    public User getUserInfo(String username) {
        if (username == null) {
            return  null;
        }
        return DBManager.getInstance().getUserInfo(username);
    }
}
