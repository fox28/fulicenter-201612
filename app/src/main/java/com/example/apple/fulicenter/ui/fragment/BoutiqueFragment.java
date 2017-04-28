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
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.BoutiqueBean;
import com.example.apple.fulicenter.model.net.BoutiqueModel;
import com.example.apple.fulicenter.model.net.IBoutiqueModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.OkHttpUtils;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.BoutiqueAdapter;
import com.example.apple.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {
    private static final String TAG = BoutiqueFragment.class.getSimpleName();

    IBoutiqueModel mModel;
    LinearLayoutManager mManager;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    Unbinder unbinder;


    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_new_goods, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mModel = new BoutiqueModel();
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



    private void initData() {
        mModel.downloadData(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                setRefresh(false);
                L.e(TAG, "initData, result :" +result);
                if (result != null && result.length > 0) {
                    L.e(TAG, "initData, result长度" + result.length);
                    ArrayList<BoutiqueBean> listArr = ResultUtils.array2List(result);
                    mList.clear();
                    mList.addAll(listArr);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "error:"+error);
                CommonUtils.showShortToast(error);
                setRefresh(false);

            }
        });
    }



    private void initView() {
        // 设置刷新操作的颜色
        mSrl.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow));

        mManager = new LinearLayoutManager(getContext());
        mRvGoods.setLayoutManager(mManager);

        //  setHasFixedSize()方法用来使RecyclerView保持固定的大小,该信息被用于自身的优化。
        mRvGoods.setHasFixedSize(true);
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));

        mList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        mRvGoods.setAdapter(mAdapter);


    }
    private void setRefresh(boolean refresh) {
        mSrl.setRefreshing(refresh);
        mTvRefresh.setVisibility(refresh? View.VISIBLE:View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
