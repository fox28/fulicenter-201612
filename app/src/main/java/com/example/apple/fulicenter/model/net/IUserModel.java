package com.example.apple.fulicenter.model.net;

import android.content.Context;

/**
 * Created by apple on 2017/4/5.
 */

public interface IUserModel {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void register(Context context, String username, String nick, String password,
                  OnCompleteListener<String> listener);
}
