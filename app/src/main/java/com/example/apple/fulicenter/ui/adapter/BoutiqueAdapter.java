package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.ui.activity.BoutiqueChildActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/29.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;



    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueHolder holder = new BoutiqueHolder(View.inflate(mContext,
                R.layout.item_boutique, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {

        BoutiqueHolder holder = (BoutiqueHolder) parentHolder;
        final BoutiqueBean bean = mList.get(position);
        holder.mTvBoutiqueTitle.setText(bean.getTitle());
        holder.mTvBoutiqueName.setText(bean.getName());
        holder.mTvBoutiqueDescription.setText(bean.getDescription());
        ImageLoader.downloadImg(mContext, holder.mIvBoutiqueImg, bean.getImageurl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class).
                        putExtra(I.NewAndBoutiqueGoods.CAT_ID, bean.getId())
                        .putExtra(I.Boutique.TITLE, bean.getTitle()));
            }
        });



    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size()  : 0;
    }





    class BoutiqueHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueHolder(View view)  {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
