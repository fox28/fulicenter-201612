package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by apple on 2017/3/29.
 */

public class BoutiqueModel implements IBoutiqueModel {
    @Override
    public void downloadData(Context context, OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }
}
