package com.example.apple.fulicenter.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.dao.UserDao;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.OnSetAvatarListener;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/7.
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    OnSetAvatarListener mOnSetAvatarListener;
    User user;
    String avatarName;


    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.iv_user_profile_avatar)
    ImageView mIvUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView mTvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mTvUserProfileNick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mTvCommonTitle.setText(getString(R.string.user_profile));
        initData();
    }

    //修改昵称后界面更新数据
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        user = FuLiCenterApplication.getCurrentUser();
        if (user != null) {
            showUserInfo(user);
        } else {
            onBackClick();
        }
    }

    private void showUserInfo(User user) {
        mTvUserProfileName.setText(user.getMuserName());
        mTvUserProfileNick.setText(user.getMuserNick());
        ImageLoader.downloadImg(SettingsActivity.this, mIvUserProfileAvatar, user.getAvatar());
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(SettingsActivity.this);
    }

    @OnClick(R.id.layout_user_profile_avatar)
    public void avatarOnClick() {
        mOnSetAvatarListener = new OnSetAvatarListener(SettingsActivity.this, R.id.layout_user_profile_avatar,
                getAvatarName(), I.AVATAR_TYPE);
    }
    /**
     * 获取头像文件的名字
     * @return
     */
    private String getAvatarName() {
        avatarName = user.getMuserName()+System.currentTimeMillis();
        L.e(TAG, "avatarName = " + avatarName);
        return  avatarName;
    }

    @OnClick(R.id.layout_user_profile_username)
    public void usernameOnClick() {
        CommonUtils.showShortToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void updateNick() {
        MFGT.gotoUpdateNickActivity(SettingsActivity.this);
    }

    @OnClick(R.id.btn_logout)
    public void onLogout() {
        UserDao.getInstance(SettingsActivity.this).logout();
        finish();
        MFGT.gotoLoginActivity(SettingsActivity.this, I.REQUEST_CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri data1 = getIntent().getData();
            if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
                uploadAvatar();
            }
            mOnSetAvatarListener.setAvatar(requestCode,data, mIvUserProfileAvatar);
        }
    }

    /**
     * 上传头像设置
     */
    private void uploadAvatar() {

    }
}
