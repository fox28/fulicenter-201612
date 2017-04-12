package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.AlbumsBean;
import com.example.apple.fulicenter.model.bean.GoodsDetailsBean;
import com.example.apple.fulicenter.model.bean.MessageBean;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.net.GoodsModel;
import com.example.apple.fulicenter.model.net.IGoodsModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.ui.view.FlowIndicator;
import com.example.apple.fulicenter.ui.view.MFGT;
import com.example.apple.fulicenter.ui.view.SlideAutoLoopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/3/30.
 */
public class GoodsDetailActivity extends AppCompatActivity {
    IGoodsModel model;
    int goodsId = 0;
    GoodsDetailsBean bean;
    boolean isCollect;

    @BindView(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView mSalv;
    @BindView(R.id.flowIndicator)
    FlowIndicator mFlowIndicator;
    @BindView(R.id.wv_good_brief)
    WebView mWvGoodBrief;
    @BindView(R.id.iv_good_collect)
    ImageView mIvGoodCollect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(GoodsDetailActivity.this);
            return;
        }
        model = new GoodsModel();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        // 只有当bean为空的时候，才加载GoodsDetailsBean bean ，否则单独下载收藏状态loadCollectStatus
        if (bean == null) {
            model.downloadData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
                @Override
                public void onSuccess(GoodsDetailsBean result) {
                    if (result != null) {
                        bean = result;
                        showDetails();
                    }
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(error);// 提示失败信息
                }
            });
        }
        loadCollectStatus();
    }

    private void loadCollectStatus() {
        User user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            model.operateCollect(GoodsDetailActivity.this, I.ACTION_IS_COLLECT, goodsId, user.getMuserName(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean msg) {
                            if (msg != null && msg.isSuccess()) {
                                isCollect =true;

                            } else {
                                isCollect =false;
                            }
                            setCollectStatus();
                        }

                        @Override
                        public void onError(String error) {
                            isCollect =false;
                            setCollectStatus();
                        }
                    });
        }
    }

    /**
     * 设置收藏图标的状态，mIvGoodCollect
     * id：iv_good_collect
     */
    private void setCollectStatus() {
        mIvGoodCollect.setImageResource(isCollect?
        R.mipmap.bg_collect_out:R.mipmap.bg_collect_in);
    }

    private void showDetails() {
        mTvGoodNameEnglish.setText(bean.getGoodsEnglishName());
        mTvGoodName.setText(bean.getGoodsName());
        mTvGoodPriceShop.setText(bean.getShopPrice());
        mTvGoodPriceCurrent.setText(bean.getCurrencyPrice());
        mSalv.startPlayLoop(mFlowIndicator, getAlbumUrl(), getAlbumCount());
        mWvGoodBrief.loadDataWithBaseURL(null, bean.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);

    }

    private int getAlbumCount() {
        if (bean.getProperties() != null && bean.getProperties().length > 0) {
            return bean.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumUrl() {
        if (bean.getProperties() != null && bean.getProperties().length > 0) {
            AlbumsBean[] albums = bean.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    @OnClick(R.id.backClickArea)
    public void onBackClicked() {
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_collect)
    public void onOperateCollect() {
        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            MFGT.gotoLoginActivity(this, 0);
        } else{
            if (isCollect) {
                model.operateCollect(GoodsDetailActivity.this, I.ACTION_DELETE_COLLECT, goodsId, user.getMuserName(),
                        new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean msg) {
                                if (msg != null && msg.isSuccess()) {
                                    isCollect = false;

                                } else {
                                    isCollect = true;
                                }
                                setCollectStatus();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
            } else {
                model.operateCollect(GoodsDetailActivity.this, I.ACTION_ADD_COLLECT, goodsId, user.getMuserName(),
                        new OnCompleteListener<MessageBean>() {
                            @Override
                            public void onSuccess(MessageBean msg) {
                                if (msg != null && msg.isSuccess()) {
                                    isCollect =true;

                                } else {
                                    isCollect =false;
                                }
                                setCollectStatus();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
            }
        }
    }
}
