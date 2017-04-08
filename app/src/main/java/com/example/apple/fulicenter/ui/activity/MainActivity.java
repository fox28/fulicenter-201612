package com.example.apple.fulicenter.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.ui.fragment.BoutiqueFragment;
import com.example.apple.fulicenter.ui.fragment.CategoryFragment;
import com.example.apple.fulicenter.ui.fragment.NewGoodsFragment;
import com.example.apple.fulicenter.ui.fragment.PersonalCenterFragment;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;
    int index = 0;
    int currentIndex = 0;
    RadioButton[] mRadioButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

        initFragment();
        initRadioButton();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .hide(mBoutiqueFragment).hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initRadioButton() {
        mRadioButtons = new RadioButton[5];
        mRadioButtons[0] = mLayoutNewGood;
        mRadioButtons[1] = mLayoutBoutique;
        mRadioButtons[2] = mLayoutCategory;
        mRadioButtons[3] = mLayoutCart;
        mRadioButtons[4] = mLayoutPersonalCenter;
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        mFragments[4] = mPersonalCenterFragment;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                if (FuLiCenterApplication.getCurrentUser() == null) {
                    MFGT.gotoLoginActivity(MainActivity.this, I.REQUEST_CODE_LOGIN_FROM_CART);
                } else {
                    index = 3;
                }
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getCurrentUser() == null) {
                    MFGT.gotoLoginActivity(MainActivity.this, I.REQUEST_CODE_LOGIN);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
    }

    private void setFragment() {
        /*if (currentIndex != index) {
            getSupportFragmentManager().beginTransaction()
                    .show(mFragments[index]).hide(mFragments[currentIndex]).commit();
            currentIndex = index;
        }*/
        if (currentIndex != index) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mFragments[currentIndex]);
            if (!mFragments[index].isAdded()) {
                transaction.add(R.id.fragment_container, mFragments[index]);
            }
            transaction.show(mFragments[index]).commit();
            currentIndex = index;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG, "onActivityResult, index = "+index+", currentIndex = "+ currentIndex);
        if (resultCode == RESULT_OK) {
            // 点击购物车
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }
            // 点击个人中心
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            setFragment();
            setRadioButton();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e(TAG, "onResume, index = "+index+", currentIndex = "+ currentIndex);
        setRadioButton();
    }


    // 避免返回到MainActivity后底部button颜色显示不正确
    private void setRadioButton() {
        if (currentIndex >= 0 && currentIndex < mRadioButtons.length) {
            mRadioButtons[currentIndex].setChecked(true);
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
