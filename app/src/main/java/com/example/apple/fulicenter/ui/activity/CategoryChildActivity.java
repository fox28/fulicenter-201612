package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;
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

    @BindView(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);

        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClicked() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onSortClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sort_price:
                sortBy = sortPrice?I.SORT_BY_PRICE_ASC:I.SORT_BY_PRICE_DESC;
                sortPrice = !sortPrice;
                break;
            case R.id.btn_sort_addtime:
                sortBy = sortAddtime?I.SORT_BY_ADDTIME_ASC:I.SORT_BY_ADDTIME_DESC;
                sortAddtime = !sortAddtime;
                break;
        }
        mNewGoodsFragment.setSortBy(sortBy);

    }
}
