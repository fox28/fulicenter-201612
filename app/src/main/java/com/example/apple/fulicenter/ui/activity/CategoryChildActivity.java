package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/2.
 */
public class CategoryChildActivity extends AppCompatActivity {

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

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClicked() {
        MFGT.finish(this);
    }
}
