package com.example.apple.fulicenter.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.bean.CategoryChildBean;
import com.example.apple.fulicenter.model.bean.CategoryGroupBean;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.ui.view.MFGT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/31.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    private static final String TAG = CategoryAdapter.class.getSimpleName();
    Context mContext;
    List<CategoryGroupBean> groupList;
    List<List<CategoryChildBean>> childList;


    public CategoryAdapter(Context context) {
        mContext = context;
        this.groupList = new ArrayList<>();
        this.childList = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return groupList != null ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        /**
         * childList.get(groupPosition).size() 有过出错
         */
        return childList != null && childList.get(groupPosition) != null ?
                childList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupList.get(groupPosition).getId();
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition, isExpanded);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.bind(groupPosition, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(List<CategoryGroupBean> groupList, List<List<CategoryChildBean>> childList) {
        Log.e(TAG, "标记4：initData,groupList = " + groupList.size() + "initData, childList = " + childList);
        this.groupList.clear();
        this.childList.clear();
        this.groupList = groupList;
        this.childList = childList;
        Log.e(TAG, "标记5：initData,this.groupList = " + this.groupList.size() +
                "initData, this.childList = " + this.childList);

        notifyDataSetChanged();
    }

    class GroupViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView mIvGroupThumb;
        @BindView(R.id.iv_group_name)
        TextView mIvGroupName;
        @BindView(R.id.iv_group_expand)
        ImageView mIvGroupExpand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int groupPosition, boolean isExpanded) {
            CategoryGroupBean bean = getGroup(groupPosition);
//        L.e(TAG, "标记6：group = " + bean.getName()) ;
            mIvGroupName.setText(bean.getName());
            mIvGroupExpand.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
            ImageLoader.downloadImg(mContext, mIvGroupThumb, bean.getImageUrl());
        }
    }



    class ChildViewHolder {
        @BindView(R.id.iv_child_thumb)
        ImageView mIvChildThumb;
        @BindView(R.id.iv_child_name)
        TextView mIvChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout mLayoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(int groupPosition, int childPosition) {
            final CategoryChildBean bean = getChild(groupPosition, childPosition);
//        L.e(TAG, "标记7：child = " + bean.getName()) ;
            if (bean != null) {
                mIvChildName.setText(bean.getName());
                ImageLoader.downloadImg(mContext, mIvChildThumb, bean.getImageUrl());

                // 监听事件选择mLayoutCategoryChild，分类小组数据的布局！！
                mLayoutCategoryChild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MFGT.toCategoryChildActivity(mContext, bean.getId());
                    }
                });

            }
        }
    }
}


