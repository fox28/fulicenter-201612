package com.example.apple.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.CartModel;
import com.example.apple.fulicenter.model.net.ICartModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.CartAdapter;
import com.example.apple.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by apple on 2017/4/13.
 */

public class CartFragment extends Fragment {

    ICartModel model;
    LinearLayoutManager manager;
    CartAdapter adapter;
    List<CartBean> cartList;

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.tv_nothing)
    TextView mTvNothing;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @BindView(R.id.tv_cart_buy)
    TextView mTvCartBuy;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new CartModel();
        cartList = new ArrayList<>();
        initView();
        initData();
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        manager = new LinearLayoutManager(getContext());
        mRv.setLayoutManager(manager);
        mRv.setHasFixedSize(true);

        adapter = new CartAdapter(getContext(), cartList);
        mRv.setAdapter(adapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }

    private void initData() {
        User user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showCartList(user);
        }
    }

    private void showCartList(User user) {
        model.loadCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result != null) {
                    if (result.length > 0) {
                        ArrayList<CartBean> lists = ResultUtils.array2List(result);
                        cartList.addAll(lists);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
