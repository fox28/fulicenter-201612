package com.example.apple.fulicenter.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apple.fulicenter.application.I;

/**
 * Created by apple on 2017/4/7.
 */


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String FULICENTER_USER_TABLE_CREATE =
            "CREATE TABLE "+UserDao.USER_TABLE_NAME+" ("
            +UserDao.USER_COLUMN_NAME+" TEXT PRIMARY KEY, "
            +UserDao.USER_COLUMN_NICK+" TEXT,"
            +UserDao.USER_COLUMN_AVATAR+" INTEGER, "
            +UserDao.USER_COLUMN_AVATAR_PATH+" TEXT, "
            +UserDao.USER_COLUMN_AVATAR_SUFFIX+"TEXT, "
            +UserDao.USER_COLUMN_AVATAR_TYPE+" INTEGER, "
            +UserDao.USER_COLUMN_AVATAR_UPDATE_TIME+ " TEXT); ";

    private static final int DATABASE_VERSION = 1;
    private static DBOpenHelper instance;

    // 获得DBOpenHelper实例instance
    public static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context);
        }
        return instance;
    }

    // 构造方法！！！ 创建数据库
    public DBOpenHelper(Context context) {
        super(context, getDatabaseNames(), null, DATABASE_VERSION);
    }

    private static String getDatabaseNames() {
        return "com.kkk.apple.fulicenter";
    }


    // 创建表格
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FULICENTER_USER_TABLE_CREATE);
    }

    // 版本更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 关闭表格
    public void closeDB() {
        if (instance != null) {
            SQLiteDatabase db = instance.getWritableDatabase();
            db.close();
            instance = null;
        }
    }
}
