package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.NewGoodsBean;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by apple on 2017/3/27.
 */

public class NewGoodsModel implements INewGoodsModel {
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/findNewAndBoutiqueGoods?cat_id=0&page_id=1&page_size=10

    // 下载分类二级页面的请求URL
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/findGoodsDetails?cat_id=262&page_id=1&page_size=10




    @Override


    public void downloadData(Context context, int catId, int pageId, OnCompleteListener listener) {
        String url = I.REQUEST_FIND_NEW_BOUTIQUE_GOODS;
        if (catId > 0) {
            url = I.REQUEST_FIND_GOODS_DETAILS;
        }
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);


    }
}
