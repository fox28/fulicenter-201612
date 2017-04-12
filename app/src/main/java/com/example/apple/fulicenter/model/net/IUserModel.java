package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.model.bean.CollectBean;
import com.example.apple.fulicenter.model.bean.MessageBean;

import java.io.File;

/**
 * Created by apple on 2017/4/5.
 */

public interface IUserModel {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);

    void register(Context context, String username, String nick, String password,
                  OnCompleteListener<String> listener);

    void updateNick(Context context, String username, String newNick,
                    OnCompleteListener<String> listener);

    void updateAvatar(Context context, String username, File file, OnCompleteListener<String> listener);

    void loadCollectCount(Context context, String username, OnCompleteListener<MessageBean> listener);

    void loadCollects(Context context, String username, int page_id, int page_size,
                      OnCompleteListener<CollectBean[]> listener);
}
