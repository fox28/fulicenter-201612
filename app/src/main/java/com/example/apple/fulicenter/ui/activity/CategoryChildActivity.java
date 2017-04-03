package com.example.apple.fulicenter.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;
import com.example.apple.fulicenter.ui.view.CatFilterCategoryButton;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/2.
 */
public class CategoryChildActivity extends AppCompatActivity {

    boolean sortPrice;
    boolean sortAddtime;
    int sortBy = I.SORT_BY_ADDTIME_DESC;
    NewGoodsFragment mNewGoodsFragment;
    String groupName;


    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.btn_sort_price)
    Button mBtnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button mBtnSortAddtime;
    @BindView(R.id.cat_filter)
    CatFilterCategoryButton mCatFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);

        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
        groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        initView();

    }

    private void initView() {
        setGroupName();
    }

    /**
     * 设置Filter自定义控件标题：groupName
     */
    private void setGroupName() {
        mCatFilter.setText(groupName);
    }



    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onSortClicked(View view) {
        Drawable arrowOriention;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                sortPrice = !sortPrice;
                sortBy = sortPrice ? I.SORT_BY_PRICE_ASC : I.SORT_BY_PRICE_DESC;
                arrowOriention = sortPrice ? getResources().getDrawable(R.drawable.arrow_order_up) :
                        getResources().getDrawable(R.drawable.arrow_order_down);
                mBtnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, arrowOriention, null);
                break;
            case R.id.btn_sort_addtime:
                sortAddtime = !sortAddtime;
                sortBy = sortAddtime ? I.SORT_BY_ADDTIME_ASC : I.SORT_BY_ADDTIME_DESC;
                arrowOriention = sortAddtime ? getResources().getDrawable(R.drawable.arrow_order_up) :
                        getResources().getDrawable(R.drawable.arrow_order_down);
                mBtnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, arrowOriention, null);
                break;
        }
        mNewGoodsFragment.setSortBy(sortBy);

    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(CategoryChildActivity.this);
    }
}
