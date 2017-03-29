package com.example.apple.fulicenter.model.net;


import android.content.Context;

import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by apple on 2017/3/27.
 */

public interface IBoutiqueModel {
    void downloadData(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
