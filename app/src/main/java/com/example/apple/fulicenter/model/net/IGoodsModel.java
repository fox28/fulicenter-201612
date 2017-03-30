package com.example.apple.fulicenter.model.net;


import android.content.Context;

import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;

/**
 * Created by apple on 2017/3/27.
 */

public interface IGoodsModel {
    void downloadData(Context context, int goodsId,OnCompleteListener<GoodsDetailsBean> listener);
}
