package com.example.apple.fulicenter.model.net;


import android.content.Context;

import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.MessageBean;

/**
 * Created by apple on 2017/3/27.
 */

public interface ICartModel {
    void loadCart(Context context, String userName, OnCompleteListener<CartBean[]> listener);

    void CartAction(Context context, int action, String cartId, String goodsId, String userName,
                    int count, OnCompleteListener<MessageBean> listener);
}
