package com.example.apple.fulicenter.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.bean.CategoryChildBean;
import com.example.apple.fulicenter.ui.adapter.CatFilterAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/4/3.
 */

public class CatFilterCategoryButton extends Button {
    Context mContext;
    PopupWindow mPopupWindow;
    boolean isExpand = true;

    GridView mGView;
    CatFilterAdapter mAdapter;


    /**
     * butterKnife实例化
     * @param context
     * @param attrs
     */
    public CatFilterCategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCatFilterOnClickListener();
    }

    public void setGroupNameAndChlidlist(String groupName, ArrayList<CategoryChildBean> list) {
        // 设置自定义控件标题
        this.setText(groupName);

        mGView = new GridView(mContext);
        mGView.setHorizontalSpacing(10);
        mGView.setVerticalSpacing(10);
        mGView.setNumColumns(GridView.AUTO_FIT);

        mAdapter = new CatFilterAdapter(mContext,list,groupName);
        mGView.setAdapter(mAdapter);

    }


    private void setCatFilterOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    initPopupWindow();
                } else {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrowOrientation();
            }
        });
    }

    private void setArrowOrientation() {
        Drawable end = getResources().getDrawable(isExpand?
                R.drawable.arrow2_up:R.drawable.arrow2_down);
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, end, null);
        isExpand = !isExpand;
    }

    private void initPopupWindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(this);
            mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xdd000000));
            /*TextView tv = new TextView(mContext);
            tv.setTextSize(23);
            tv.setTextColor(getResources().getColor(R.color.red));
            tv.setText("测试文字测试文字测试文字测试文字测试文字");*/
            mPopupWindow.setContentView(mGView);
        }
        mPopupWindow.showAsDropDown(this);
    }
}
