package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by apple on 2017/3/30.
 */

public class GoodsModel implements IGoodsModel {
    @Override
    public void downloadData(Context context, int goodsId, OnCompleteListener<GoodsDetailsBean> listener) {
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/findGoodDetails?goods_id=7672
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);

    }
}
