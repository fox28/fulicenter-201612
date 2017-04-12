package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.CollectBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.IUserModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.net.UserModel;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.CollectsAdapter;
import com.example.apple.fulicenter.ui.view.MFGT;
import com.example.apple.fulicenter.ui.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by apple on 2017/4/12.
 */

public class CollectsActivity extends AppCompatActivity {
    private static final String TAG = "CollectsActivity";
    GridLayoutManager manager;
    IUserModel model;
    List<CollectBean> mList = new ArrayList<>();
    CollectsAdapter adapter;
    int pageId = 1;

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv_goods)
    RecyclerView mRvGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.tv_nomore)
    TextView mTvNomore;
    Unbinder unbinder;
    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        unbinder = ButterKnife.bind(this);
        model = new UserModel();
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
        manager = new GridLayoutManager(this, I.COLUM_NUM);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mList.size()) {
                    return I.COLUM_NUM;
                }
                return 1;
            }
        });
        mRvGoods.setLayoutManager(manager);
        mRvGoods.setHasFixedSize(true);
        adapter = new CollectsAdapter(this, mList);
        mRvGoods.setAdapter(adapter);
        // 增加间距
        mRvGoods.addItemDecoration(new SpaceItemDecoration(12));
        mTvCommonTitle.setText(getString(R.string.collect_title));// 收藏列表添加title
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        mRvGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPositon = manager.findLastVisibleItemPosition();
                if (newState == recyclerView.SCROLL_STATE_IDLE
                        && adapter.isMore()
                        && lastPositon == adapter.getItemCount() - 1) {
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = manager.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition == 0);
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
        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            return;
        }
        model.loadCollects(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT,
                new OnCompleteListener<CollectBean[]>() {
                    @Override
                    public void onSuccess(CollectBean[] result) {
                        // 设置SwipeRefreshLayout为不再刷新
               /* mSrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);*/
                        setRefresh(false);
                        adapter.setMore(true);
                        L.e(TAG, "initData, result = " + result);
                        if (result != null && result.length > 0) {
                            L.e(TAG, "initData, result = " + result.length);
                            ArrayList<CollectBean> lists = ResultUtils.array2List(result);
                            if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                                mList.clear();
                            }
                            mList.addAll(lists); // 添加list
                            if (lists.size() < I.PAGE_ID_DEFAULT) {
                                // 当前界面没有填满商品、或列表长度小于默认页面长度值
                                adapter.setMore(false);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        L.e(TAG, "initData, error = " + error);
                        CommonUtils.showShortToast(error);
                        setRefresh(false);
                    }
                });
    }

    private void setRefresh(boolean refresh) {
        if (mSrl != null) {
            mSrl.setRefreshing(refresh);
        }
        if (mTvRefresh != null) {
            mTvRefresh.setVisibility(refresh ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.backClickArea)
    public void onViewClicked() {
        MFGT.finish(CollectsActivity.this);
    }
}
