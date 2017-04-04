package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.bean.CategoryChildBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/4/4.
 */
public class CatFilterAdapter extends BaseAdapter {
    Context mContext;
    List<CategoryChildBean> mList;

    public CatFilterAdapter(Context context, List<CategoryChildBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CategoryChildBean getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatFilterViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_cat_filter, null);
            holder = new CatFilterViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CatFilterViewHolder) convertView.getTag();
        }

        // 绑定相关数据
        holder.bind(position);
        return convertView;
    }

    class CatFilterViewHolder {
        @BindView(R.id.ivCategoryChildThumb)
        ImageView mIvCategoryChildThumb;
        @BindView(R.id.tvCategoryChildName)
        TextView mTvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mLayoutCategoryChild;

        CatFilterViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        public void bind(int position) {
            CategoryChildBean bean = getItem(position);
            mTvCategoryChildName.setText(bean.getName());
            ImageLoader.downloadImg(mContext,mIvCategoryChildThumb,bean.getImageUrl());
        }
    }
}
