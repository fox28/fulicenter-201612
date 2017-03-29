package com.example.apple.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/30.
 */

public class BoutiqueChildActivity extends AppCompatActivity {

    @BindView(R.id.ivBackBoutiqueChild)
    ImageView mIvBackBoutiqueChild;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();

    }

    public void onCheckekChange(View view) {
        switch (view.getId()) {
            case R.id.ivBackBoutiqueChild:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}
