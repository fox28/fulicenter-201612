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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.CartModel;
import com.example.apple.fulicenter.model.net.ICartModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.L;
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
    private static final String TAG = "CartFragment";
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
    @BindView(R.id.layout_cart_check)
    RelativeLayout mLayoutCart;


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
        setListener();
    }

    private void setListener() {
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefresh(true);
                initData();
            }
        });
    }

    private void setRefresh(boolean refresh) {
        mSrl.setRefreshing(refresh);
        mTvRefresh.setVisibility(refresh ? View.VISIBLE : View.GONE);
    }

    private void setCarListLayout(boolean isShow) {
        mTvNothing.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mLayoutCart.setVisibility(isShow ? View.VISIBLE : View.GONE);
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
        setCarListLayout(false);
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
                setRefresh(false);
                setCarListLayout(true);
                if (result != null) {
                    cartList.clear();
                    if (result.length > 0) {
                        ArrayList<CartBean> lists = ResultUtils.array2List(result);
                        cartList.addAll(lists);
                        adapter.notifyDataSetChanged();
                    } else {
                        setCarListLayout(false);
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "showCartList, error=" + error);
                setRefresh(false);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
