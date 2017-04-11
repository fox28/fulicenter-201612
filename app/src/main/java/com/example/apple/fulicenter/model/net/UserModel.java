package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

import java.io.File;

/**
 * Created by apple on 2017/4/5.
 */

public class UserModel implements IUserModel {
//  http://101.251.196.90:8080/FuLiCenterServerV2.0/login?m_user_name=12345&m_user_password=1345
    @Override
    public void login(Context context, String username, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .execute(listener);
    }

// http://101.251.196.90:8080/FuLiCenterServerV2.0/register?m_user_name=123456&m_user_nick=ada&m_user_password=1345
    @Override
    public void register(Context context, String username, String nick, String password,
                         OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.PASSWORD, password)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    @Override
    public void updateNick(Context context, String username, String newNick, OnCompleteListener<String> listener) {
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/updateNick?m_user_name=qwer159&m_user_nick=123
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, newNick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener) {
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/updateAvatar?name_or_hxid=qwer159&avatarType=user_avatar
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, username)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .targetClass(String.class)
                .addFile2(file)
                .post()
                .execute(listener);
    }
}
