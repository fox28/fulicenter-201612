package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

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
}
