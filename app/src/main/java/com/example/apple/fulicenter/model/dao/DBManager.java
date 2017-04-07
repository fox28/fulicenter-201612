package com.example.apple.fulicenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apple.fulicenter.model.bean.User;

/**
 * Created by apple on 2017/4/7.
 * 主要有两个方法
 * getUserInfo
 * initUserInfo
 *
 */

public class DBManager {
    DBOpenHelper mHelper;
    static DBManager dbmanager = new DBManager();

    public synchronized void initDB(Context context) {
        // 创建数据库！
        mHelper = new DBOpenHelper(context);
    }
    public static DBManager getInstance() {
        return dbmanager;
    }

    public boolean saveUserInfo(User user) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        if (database.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUMN_AVATAR,user.getMavatarId());
            values.put(UserDao.USER_COLUMN_AVATAR_PATH,user.getMavatarPath());
            values.put(UserDao.USER_COLUMN_AVATAR_TYPE,user.getMavatarType());
            values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX,user.getMavatarSuffix());
            values.put(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME,user.getMavatarLastUpdateTime());
            // 返回值为插入记录的行号，-1表示插入失败
            return database.insert(UserDao.USER_TABLE_NAME,null,values) != -1;
        }
        return false;
    }

    public User getUserInfo(String username) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if (db.isOpen()) {
            String sql = "SELECT * FROM " + UserDao.USER_TABLE_NAME
                    + " where " + UserDao.USER_COLUMN_NAME + " = '" + username + "'";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.setMuserName(username);
                user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_NICK)));
                user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR)));
                user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH)));
                user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE)));
                user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX)));
                user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.USER_COLUMN_AVATAR_UPDATE_TIME)));
                return user;
            }
        }
        return null;
    }

}
