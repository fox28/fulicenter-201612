package com.example.apple.fulicenter.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.CartBean;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.bean.MessageBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.CartModel;
import com.example.apple.fulicenter.model.net.ICartModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.CartAdapter;
import com.example.apple.fulicenter.ui.view.MFGT;
import com.example.apple.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    UpdateReceiver mUpdateReceiver;
    int sumPrice ;
    int rankPrice ;

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
        adapter.setListener(mOnCheckedChangeListener);
        adapter.setUpdateListener(updateListener);

        mUpdateReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        getContext().registerReceiver(mUpdateReceiver, filter); // 注册监听事件：参数为监听实例和过滤条件。别忘了取消注册。
    }

    /**
     * 实例化监听事件更新（增加减少）购物车商品数量
     */
    View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            int count = 0;
            if (view.getTag(R.id.action_add_cart)!=null) {
                count = (int) view.getTag(R.id.action_add_cart);
            } else if (view.getTag(R.id.action_del_cart)!=null) {
                count = (int) view.getTag(R.id.action_del_cart);
            }
            L.e(TAG, "addListener, position="+position+", count="+count);
            updateCart(position, count);
        }
    };

    private void updateCart(final int position, final int count) {
        CartBean bean = cartList.get(position);
        GoodsDetailsBean goods = bean.getGoods();
        // 判断条件：当count=0时，执行删除操作
        int action = bean.getCount()+count==0?I.ACTION_CART_DEL:I.ACTION_CART_UPDATA;
        if (bean != null) {
            model.CartAction(getContext(), action, String.valueOf(bean.getId()),String.valueOf(goods.getGoodsId()),
                      FuLiCenterApplication.getCurrentUser().getMuserName(),
                    bean.getCount() + count, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                updateCartListView(position, count);
                            }
                        }
                        @Override
                        public void onError(String error) {
                            L.e(TAG, "updateCart, error="+error);
                        }
                    });
        }
    }

    private void updateCartListView(int position, int count) {
        L.e(TAG, "updateCartListView, position="+position+",    count="+count);
        if (cartList.get(position).getCount()+count == 0) {// 判断条件：当count=0时，执行删除操作
            cartList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, cartList.size()-position-1);
        }else {
            cartList.get(position).setCount(cartList.get(position).getCount()+count);
            adapter.notifyItemChanged(position); // 更新列表数据
        }
        L.e(TAG, "updateCartListView, notify is doing ...");
        setPriceText(); // 更新购物车选中商品的累积价格
        setCarListLayout(!cartList.isEmpty());// 当购物车数量为0时，显示"空空如也"提示信息
    }

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int position = (int) compoundButton.getTag();
            L.e(TAG, "onCheckedChanged, checked=" + isChecked + ", position=" + position);
            cartList.get(position).setChecked(isChecked);
            setPriceText();
        }
    };

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

        // 设置价格
        setPriceText();
    }

    private void setPriceText() {
        sumPrice = 0;
        rankPrice = 0;
        for (CartBean bean : cartList) {
            if (bean.isChecked()) {
                GoodsDetailsBean goods = bean.getGoods();
                if (goods != null) {
                    sumPrice+=getPrice(goods.getCurrencyPrice())*bean.getCount();
                    rankPrice+=getPrice(goods.getRankPrice())*bean.getCount();
                }
            }
        }
        mTvCartSumPrice.setText("合计：￥"+sumPrice);
        mTvCartSavePrice.setText("节省：￥"+(sumPrice-rankPrice));
    }

    private int getPrice(String price) {
        String temp = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(temp);
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
                cartList.clear();
                setPriceText();
                if (result != null) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUpdateReceiver != null) {
            getContext().unregisterReceiver(mUpdateReceiver);
        }
    }

    class UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }

    @OnClick(R.id.tv_cart_buy)
    public void orderBuy(){
        if (sumPrice > 0) {
            MFGT.gotoOrderActivity(getActivity(), rankPrice);
        } else {
            CommonUtils.showLongToast(R.string.order_nothing);
        }
    }
}
