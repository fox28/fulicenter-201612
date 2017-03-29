package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/29.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;

    boolean isMore;
    public boolean isMore() {
        return isMore;
    }
    public void setMore(boolean more) {
        isMore = more;
    }

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new BoutiqueHolder(View.inflate(mContext, R.layout.item_boutique, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        switch (getItemViewType(position)) {
            case I.TYPE_FOOTER:
                FooterHolder holder = (FooterHolder) parentHolder;
                holder.mTvFooter.setText(getFooterString());
                break;
            case I.TYPE_ITEM:
                BoutiqueHolder holder1 = (BoutiqueHolder) parentHolder;
                holder1.mTvBoutiqueTitle.setText(mList.get(position).getTitle());
                holder1.mTvBoutiqueName.setText(mList.get(position).getName());
                holder1.mTvBoutiqueDescription.setText(mList.get(position).getDescription());
                ImageLoader.downloadImg(mContext, holder1.mIvBoutiqueImg,
                        mList.get(position).getImageurl());

        }

    }

    private int getFooterString() {
        return isMore? R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView mTvFooter;

        FooterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
