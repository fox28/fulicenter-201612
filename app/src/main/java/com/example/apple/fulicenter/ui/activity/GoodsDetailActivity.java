package com.example.apple.fulicenter.ui.activity;

import android.content.Intent;
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
import com.example.apple.fulicenter.model.net.CartModel;
import com.example.apple.fulicenter.model.net.GoodsModel;
import com.example.apple.fulicenter.model.net.ICartModel;
import com.example.apple.fulicenter.model.net.IGoodsModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.ui.utils.AntiShake;
import com.example.apple.fulicenter.ui.view.FlowIndicator;
import com.example.apple.fulicenter.ui.view.MFGT;
import com.example.apple.fulicenter.ui.view.SlideAutoLoopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by apple on 2017/3/30.
 */
public class GoodsDetailActivity extends AppCompatActivity {
    AntiShake util = new AntiShake();

    private static final String TAG = "GoodsDetailActivity";
    IGoodsModel model;
    ICartModel cartModel;
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
        cartModel = new CartModel();
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
            operateCollect(I.ACTION_IS_COLLECT, user);
        }
    }

    /**
     * mIvGoodCollect对于收藏的操作集合在一起
     * @param action
     * @param user
     */
    private void operateCollect(final int action, User user) {
        model.operateCollect(GoodsDetailActivity.this, action, goodsId, user.getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {

                        if (msg != null && msg.isSuccess()) {
//                            isCollect = true;
//                            if (action == I.ACTION_DELETE_COLLECT) {
//                                isCollect = false;
//                            }
                            isCollect = action==I.ACTION_DELETE_COLLECT?false:true;
                            if (action != I.ACTION_IS_COLLECT) {
                                CommonUtils.showShortToast(msg.getMsg());
                            }
                        } else {
//                            isCollect =false;
//                            if (action == I.ACTION_DELETE_COLLECT) {
//                                isCollect =true;
//                            }
                            isCollect = action==I.ACTION_IS_COLLECT? false:isCollect;
                        }
                        setCollectStatus();
                    }

                    @Override
                    public void onError(String error) {
                        L.e(TAG, "error = " +error);
                        if (action == I.ACTION_IS_COLLECT) {
                            isCollect =false;
                            setCollectStatus();
                        }
                    }
                });
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
        setResult(RESULT_OK, new Intent()
                .putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId)
                .putExtra(I.GoodsDetails.KEY_IS_COLLECT, isCollect));
        MFGT.finish(this);
    }

    @OnClick(R.id.iv_good_collect)
    public void onCollectGoods() {
        if(util.check()) return;
        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            MFGT.gotoLoginActivity(this, 0);
        } else{
            if (isCollect) {// 取消收藏
                operateCollect(I.ACTION_DELETE_COLLECT, user);

            } else { // 增加收藏
                operateCollect(I.ACTION_ADD_COLLECT, user);
            }
        }
    }

    @OnClick(R.id.iv_good_cart)
    public void onGoodsCart() {
        if(util.check()) return; // 添加防抖措施
        User user = FuLiCenterApplication.getCurrentUser();
        if (user == null) {
            MFGT.gotoLoginActivity(GoodsDetailActivity.this, 0);
        } else {
            addGoodsToCart(user);
        }
    }

    private void addGoodsToCart(User user) {
        cartModel.CartAction(GoodsDetailActivity.this, I.ACTION_CART_ADD, null, "" + goodsId,
                user.getMuserName(), 1, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean msg) {
                        if (msg != null && msg.isSuccess()) {
                            CommonUtils.showShortToast(R.string.add_goods_success);
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                        } else {
                            CommonUtils.showShortToast(R.string.add_goods_fail);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showShortToast(R.string.add_goods_fail);
                        L.e(TAG, "addGoodsToCart, error = "+error);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.e(TAG, "onDestroy, isCollect="+isCollect);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        L.e(TAG, "onBackPressed, isCollect="+isCollect);
        onBackClicked();
    }

    @OnClick(R.id.iv_good_share)
    public void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
