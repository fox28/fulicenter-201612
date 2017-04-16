package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.model.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/4/14.
 */

public class CartAdapter extends RecyclerView.Adapter {
    private static final String TAG = "CartAdapter";
    Context mContext;
    List<CartBean> mList;
    CompoundButton.OnCheckedChangeListener listener;
    View.OnClickListener updateListener;

    public void setUpdateListener(View.OnClickListener listener) {
        this.updateListener = listener;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public CartAdapter(Context context, List<CartBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, int position) {
        CartViewHolder holder = (CartViewHolder) parentHolder;
        L.e(TAG, "onBindViewHolder, bind... ...");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.tv_cart_count)
        TextView mTvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            mCbCartSelected.setOnCheckedChangeListener(null);
            User user = FuLiCenterApplication.getCurrentUser();
            if (user != null) {
                CartBean bean = mList.get(position);
                L.e(TAG, "bind, position="+position+", bean="+bean.isChecked());
                mCbCartSelected.setChecked(bean.isChecked());
                mTvCartCount.setText("("+bean.getCount()+")");
                GoodsDetailsBean goods = bean.getGoods();
                if (goods != null) {
                    mTvCartPrice.setText(goods.getCurrencyPrice());
                    mTvCartGoodName.setText(goods.getGoodsName());
                    ImageLoader.downloadImg(mContext,mIvCartThumb,goods.getGoodsThumb());
                }
                mCbCartSelected.setTag(position);
                mCbCartSelected.setOnCheckedChangeListener(listener);
                mIvCartAdd.setTag(position);
                mIvCartAdd.setTag(R.id.action_add_cart,1);
                mIvCartAdd.setOnClickListener(updateListener);
                mIvCartDel.setTag(position);
                mIvCartDel.setTag(R.id.action_del_cart,-1);
                mIvCartDel.setOnClickListener(updateListener);
            }
        }
    }
}
