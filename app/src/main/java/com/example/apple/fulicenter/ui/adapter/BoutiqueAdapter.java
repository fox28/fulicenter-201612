package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;

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
            holder.mTvBoutiqueTitle.setText(mList.get(position).getTitle());
            holder.mTvBoutiqueName.setText(mList.get(position).getName());
            holder.mTvBoutiqueDescription.setText(mList.get(position).getDescription());
            ImageLoader.downloadImg(mContext, holder.mIvBoutiqueImg,
                    mList.get(position).getImageurl());


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
