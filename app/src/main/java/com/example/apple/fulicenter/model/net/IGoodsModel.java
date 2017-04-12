package com.example.apple.fulicenter.model.net;


import android.content.Context;

import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.bean.MessageBean;

/**
 * Created by apple on 2017/3/27.
 */

public interface IGoodsModel {

    void downloadData(Context context, int goodsId,OnCompleteListener<GoodsDetailsBean> listener);

    void operateCollect(Context context, int action, int goodsId, String username, OnCompleteListener<MessageBean> listener);
}
