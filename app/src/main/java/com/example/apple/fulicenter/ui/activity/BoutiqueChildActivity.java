package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/3/30.
 */

public class BoutiqueChildActivity extends AppCompatActivity {


    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);

        mTvCommonTitle.setText(getIntent().getStringExtra(I.Boutique.TITLE));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();

    }


    @OnClick(R.id.backClickArea)
    public void onViewClicked() {
        finish();
    }
}
