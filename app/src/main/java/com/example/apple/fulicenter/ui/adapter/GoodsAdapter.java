package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.NewGoodsBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/27.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context context;
    List<NewGoodsBean> mList;

    /**
     *
     */
    boolean isMore;
    public boolean isMore() {
        return isMore;
    }
    public void setMore(boolean more) {
        isMore = more;
        this.notifyDataSetChanged();
    }


    public GoodsAdapter(Context context, List<NewGoodsBean> list) {
        this.context = context;
        this.mList = list;
        this.isMore = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*GoodsHolder holder = new GoodsHolder(View.inflate(context, R.layout.item_goods, null));
        View.inflate(context, R.layout.item_footer, null);*/
        RecyclerView.ViewHolder holder= null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterHolder(View.inflate(context, R.layout.item_footer, null));
        } else {
            holder =new GoodsHolder(View.inflate(context, R.layout.item_goods,null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        /*GoodsHolder holder = (GoodsHolder) parentHolder;
        NewGoodsBean bean = mList.get(position);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvGoodsPrice.setText(bean.getCurrencyPrice());
        ImageLoader.downloadImg(context, holder.ivGoodsThumb,bean.getGoodsThumb());*/
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.mTvFooter.setText(getFooterString());

            return;

        } else {
            GoodsHolder holder = (GoodsHolder) parentHolder;
            holder.tvGoodsName.setText(mList.get(position).getGoodsName());
            holder.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
            ImageLoader.downloadImg(context, holder.ivGoodsThumb, mList.get(position).getGoodsThumb());
        }

    }


    private int getFooterString() {
        return isMore? R.string.load_more:R.string.no_more;

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size()+1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    class GoodsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

     class FooterHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFooter)
        TextView mTvFooter;

        FooterHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
