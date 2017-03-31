package com.example.apple.fulicenter.model.net;


import android.content.Context;

import com.example.apple.fulicenter.model.bean.CategoryChildBean;
import com.example.apple.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by apple on 2017/3/27.
 */

public interface ICategoryModel {
    void loadGroupData(Context context, OnCompleteListener<CategoryGroupBean[]> listener);

    void loadChildData(Context context, int parentId, OnCompleteListener<CategoryChildBean[]> listener);
}
