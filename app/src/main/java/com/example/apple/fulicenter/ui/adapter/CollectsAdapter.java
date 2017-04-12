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
import com.example.apple.fulicenter.model.bean.CollectBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.ui.view.FooterHolder;
import com.example.apple.fulicenter.ui.view.MFGT;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/4/12.
 */

public class CollectsAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CollectBean> mList;
    boolean isMore;

    public CollectsAdapter(Context context, List<CollectBean> list) {
        mContext = context;
        mList = list;
        this.isMore = true;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) parentHolder;
            holder.setFooterString(getFooterString());
            return;
        }
        GoodsViewHolder holder = (GoodsViewHolder) parentHolder;
        holder.bind(position);
    }

    private int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
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

    class GoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout mLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bind(int position) {
            final CollectBean bean = mList.get(position);
            mTvGoodsName.setText(bean.getGoodsName());
            ImageLoader.downloadImg(mContext,mIvGoodsThumb,bean.getGoodsThumb());

            // 设置当前收藏商品的点击事件，单击进入商品详情
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoDetailActivity(mContext,bean.getGoodsId());
                }
            });
        }
    }
}
