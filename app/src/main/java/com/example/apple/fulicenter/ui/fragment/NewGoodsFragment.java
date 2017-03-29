package com.example.apple.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.NewGoodsBean;
import com.example.apple.fulicenter.model.net.INewGoodsModel;
import com.example.apple.fulicenter.model.net.NewGoodsModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.GoodsAdapter;
import com.example.apple.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    private static final String TAG = NewGoodsFragment.class.getSimpleName();


    INewGoodsModel model;
    int pageId = 1;

    GridLayoutManager manager;
    GoodsAdapter adapter;
    List<NewGoodsBean> mList = new ArrayList<>(); // 必须实例化，否则空指针异常

    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.rv_goods)
    RecyclerView mrvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    Unbinder unbinder;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new NewGoodsModel();
        initView();
        setListener();
        initData(I.ACTION_DOWNLOAD);
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green)
                );
        manager = new GridLayoutManager(getContext(), I.COLUM_NUM);

       manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
           @Override
           public int getSpanSize(int position) {
               if (position == mList.size()) {
                   return I.COLUM_NUM;
               }
               return 1;
           }
       });

        mrvGoods.setLayoutManager(manager);
        mrvGoods.setHasFixedSize(true);
        adapter = new GoodsAdapter(getContext(), mList);
        mrvGoods.setAdapter(adapter);
        mrvGoods.addItemDecoration(new SpaceItemDecoration(12));

    }


    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mrvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = manager.findLastVisibleItemPosition();
                if (lastPosition == adapter.getItemCount() - 1 && adapter.isMore()
                        && newState == recyclerView.SCROLL_STATE_IDLE) {
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });

    }

    private void setPullDownListener() {
        // 下拉刷新
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRefresh(true); // 调用相关方法（来源于私有方法、提取而来）
                pageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }


    private void initData(final int action) {
        model.downloadData(getContext(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                // 设置SwipeRefreshLayout为不再刷新
               /* mSrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);*/
                setRefresh(false);
                adapter.setMore(true);
                L.e(TAG, "initData, result = " + result);
                if (result != null && result.length > 0) {
                    L.e(TAG, "initData, result = " + result.length);
                    ArrayList<NewGoodsBean> lists = ResultUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mList.clear();
                    }
                    mList.addAll(lists); // 添加list
                    if (lists.size() < I.PAGE_ID_DEFAULT) {
                        adapter.setMore(false);

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "initData, error = " + error);
                // 设置SwipeRefreshLayout为不再刷新…………提取出私有方法
                setRefresh(false);
            }
        });
    }

    private void setRefresh(boolean refresh) {
        mSrl.setRefreshing(refresh);
        mtvRefresh.setVisibility(refresh ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
