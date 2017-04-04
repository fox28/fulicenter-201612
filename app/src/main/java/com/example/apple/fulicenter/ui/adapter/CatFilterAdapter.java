package com.example.apple.fulicenter.ui.adapter;

import android.app.Activity;
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
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.ui.activity.CategoryChildActivity;
import com.example.apple.fulicenter.ui.view.CatFilterCategoryButton;
import com.example.apple.fulicenter.ui.view.MFGT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/4/4.
 */
public class CatFilterAdapter extends BaseAdapter {
    private static final String TAG = "CatFilterAdapter";

    @BindView(R.id.cat_filter)
    CatFilterCategoryButton mCatFilter;

    Context mContext;
    ArrayList<CategoryChildBean> mList;
    String groupName;

    public CatFilterAdapter(Context context, ArrayList<CategoryChildBean> list, String name) {
        mContext = context;
        mList = list;
        groupName = name;
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
            final CategoryChildBean bean = getItem(position);
            mTvCategoryChildName.setText(bean.getName());
            ImageLoader.downloadImg(mContext,mIvCategoryChildThumb,bean.getImageUrl());

            // 点击item跳转到分类二级页面，并finish
            mLayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CategoryChildActivity)mContext).finish();
                    MFGT.gotoCategoryChildActivity(mContext, bean.getId(),
                            groupName, mList );
                    L.e(TAG, mTvCategoryChildName.getText().toString());
                    ((CategoryChildActivity)mContext).finish();

                }
            });
        }
    }
}
