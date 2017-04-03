package com.example.apple.fulicenter.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.ui.activity.BoutiqueChildActivity;
import com.example.apple.fulicenter.ui.activity.CategoryChildActivity;
import com.example.apple.fulicenter.ui.activity.GoodsDetailActivity;
import com.example.apple.fulicenter.ui.activity.MainActivity;

/**
 * Created by apple on 2017/3/30.
 */

public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }



    public static void startActivity(Activity activity, Class cls) {
        activity.startActivity(new Intent(activity, cls));
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);

    }


    public static void gotoMainActivity(Activity activity) {
        startActivity(activity, MainActivity.class);

    }


    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean) {

        startActivity((Activity) context, new Intent(context, BoutiqueChildActivity.class)
                .putExtra(I.NewAndBoutiqueGoods.CAT_ID, bean.getId())
                .putExtra(I.Boutique.TITLE, bean.getTitle()));
    }

    public static void gotoDetailActivity(Context context, int goodsId) {
        startActivity((Activity)context, new Intent(context, GoodsDetailActivity.class)
        .putExtra(I.Goods.KEY_GOODS_ID, goodsId));
    }

    public static void gotoCategoryChildActivity(Context context,int cat_id, String groupName) {
        startActivity((Activity)context, new Intent(context , CategoryChildActivity.class)
        .putExtra(I.NewAndBoutiqueGoods.CAT_ID, cat_id)
        .putExtra(I.CategoryGroup.NAME, groupName));

    }
}
