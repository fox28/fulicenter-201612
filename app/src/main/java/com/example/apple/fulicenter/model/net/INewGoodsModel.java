package com.example.apple.fulicenter.model.net;


import android.content.Context;

/**
 * Created by apple on 2017/3/27.
 */

public interface INewGoodsModel {
    void downloadData(Context context, int pageId,OnCompleteListener listener);
}
