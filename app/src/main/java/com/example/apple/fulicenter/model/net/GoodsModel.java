package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.bean.MessageBean;
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

    @Override
    public void operateCollect(Context context, int action, int goodsId, String username, OnCompleteListener<MessageBean> listener) {
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/isCollect?goods_id=5918&userName=qwer159
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/deleteCollect?goods_id=7672&userName=qwer159
        // http://101.251.196.90:8080/FuLiCenterServerV2.0/addCollect?goods_id=7672&userName=qwer159
        String request = I.REQUEST_IS_COLLECT;
        if (action == I.ACTION_ADD_COLLECT) {
            request = I.REQUEST_ADD_COLLECT;
        } else if (action == I.ACTION_DELETE_COLLECT) {
            request = I.REQUEST_DELETE_COLLECT;
        }
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(request)
                .addParam(I.Collect.GOODS_ID, goodsId+"")
                .addParam(I.Collect.USER_NAME, username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
