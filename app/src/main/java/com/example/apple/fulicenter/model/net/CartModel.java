package com.example.apple.fulicenter.model.net;

import android.content.Context;

import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.MessageBean;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by apple on 2017/4/13.
 */

public class CartModel implements ICartModel {
    // findCart 返回值类型为CartBean[]			userName
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/findCarts?userName=abc159
    @Override
    public void loadCart(Context context, String userName, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(CartBean[].class)
                .execute(listener);

    }


    /*
    public void cartAction(Context context, int action, String cartId, String goodsId,
                            String username, int count, OnCompleteListener<MessageBean> listener)
     */

    @Override
    public void CartAction(Context context, int action, String cartId, String goodsId, String userName,
                           int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        if (action == I.ACTION_CART_ADD) {
            addCart(utils,goodsId, userName,listener);
        }else if (action == I.ACTION_CART_DEL) {
            deleteCart(utils,cartId, listener);
        } else if (action == I.ACTION_CART_UPDATA) {
            updateCart(utils, cartId,count, listener);
        }
    }

    // addCart		goodsId userName count isChecked
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/addCart?goods_id=7672&userName=abc159&count=1&isChecked=1
    private void addCart(OkHttpUtils<MessageBean> utils, String goodsId, String userName,
                         OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID,goodsId)
                .addParam(I.Cart.USER_NAME,userName)
                .addParam(I.Cart.COUNT, String.valueOf("1"))
                .addParam(I.Cart.IS_CHECKED, String.valueOf("0"))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    // deleteCart		id
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/deleteCart?id=3025
    private void deleteCart(OkHttpUtils<MessageBean> utils, String cartId, OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, cartId)
                .targetClass(MessageBean.class)
                .execute(listener);
    }


    // updateCart		id count isChecked
    // http://101.251.196.90:8080/FuLiCenterServerV2.0/updateCart?id=3025&count=1&isChecked=1
    private void updateCart(OkHttpUtils<MessageBean> utils, String cartId, int count,
                            OnCompleteListener<MessageBean> listener) {
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, cartId)
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf("0"))
                .targetClass(MessageBean.class)
                .execute(listener);
    }




}
