package com.example.apple.fulicenter.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    Unbinder unbinder;
    INewGoodsModel model;
    int pageId = 1;

    GridLayoutManager manager;
    GoodsAdapter adapter;
    List<NewGoodsBean> mList = new ArrayList<>(); // 必须实例化，否则空指针异常

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        manager = new GridLayoutManager(getContext(), I.COLUM_NUM);
        rvGoods.setLayoutManager(manager);
        rvGoods.setHasFixedSize(true);
        adapter = new GoodsAdapter(getContext(), mList);
        rvGoods.setAdapter(adapter);
        rvGoods.addItemDecoration(new SpaceItemDecoration(12));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new NewGoodsModel();
        initData();
    }

    private void initData() {
        model.downloadData(getContext(), pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                L.e(TAG, "initData, result = " + result);
                if (result != null) {
//                    L.e(TAG, "initData, result = " + result.length);
                    ArrayList<NewGoodsBean> lists = ResultUtils.array2List(result);

                    mList.clear();
                    mList.addAll(lists); // 添加list
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "initData, error = " + error);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
