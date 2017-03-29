package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.ui.fragment.BoutiqueFragment;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_new_good)
    RadioButton mLayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mLayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mLayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mLayoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton mLayoutPersonalCenter;
    Unbinder bind;

    Fragment[] mFragments;
     NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    int index = 0;
    int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

        initFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initFragment() {
        mFragments = new Fragment[2];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (currentIndex != index) {
            getSupportFragmentManager().beginTransaction()
                    .show(mFragments[index]).hide(mFragments[currentIndex]).commit();
            currentIndex = index;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }
}
