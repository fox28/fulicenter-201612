package com.example.apple.fulicenter.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.model.bean.Result;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.dao.UserDao;
import com.example.apple.fulicenter.model.net.IUserModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.net.UserModel;
import com.example.apple.fulicenter.model.utils.CommonUtils;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.view.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/9.
 */

public class UpdateNickActivity extends AppCompatActivity {
    private static final String TAG = UpdateNickActivity.class.getSimpleName();
    IUserModel model;
    User user;
    ProgressDialog dialog;
    String newNick;

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.et_update_user_nick)
    EditText mEtUpdateUserNick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        model = new UserModel();
        user = FuLiCenterApplication.getCurrentUser();
        mTvCommonTitle.setText(R.string.update_user_nick);
        if (user == null) {
            backArea();
        } else {
            mEtUpdateUserNick.setText(user.getMuserNick());
            mEtUpdateUserNick.selectAll();
        }

    }

    @OnClick(R.id.backClickArea)
    public void backArea() {
        MFGT.finish(UpdateNickActivity.this);
    }

    @OnClick(R.id.btn_save)
    public void updateNick() {
        if (checkInput()) {
            showDialog();
            model.updateNick(this, user.getMuserName(), newNick, new OnCompleteListener<String>() {
                @Override
                public void onSuccess(String result_t) {
                    Result result = ResultUtils.getResultFromJson(result_t, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User u = (User) result.getRetData();
                            updateSuccess(u);
                        } else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                CommonUtils.showShortToast(R.string.update_nick_fail_unmodify);
                            }
                            if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                CommonUtils.showShortToast(R.string.update_fail);
                            }
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onError(String error) {
                    CommonUtils.showShortToast(R.string.update_fail);
                    dialog.dismiss();
                }
            });
        }
    }

    private void updateSuccess(final User u) {
        L.e(TAG, "updateSuccess, u = "+u);
        FuLiCenterApplication.setCurrentUser(u);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = UserDao.getInstance(UpdateNickActivity.this).saveUserInfo(u);
                L.e(TAG, "updateSuccess, b = " + b);
            }
        }).start();
        MFGT.finish(UpdateNickActivity.this);
    }

    private void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_nick));
        dialog.show();
    }

    private boolean checkInput() {
        newNick = mEtUpdateUserNick.getText().toString().trim();
        if (TextUtils.isEmpty(newNick)) {
            mEtUpdateUserNick.requestFocus();
            mEtUpdateUserNick.setError(getString(R.string.nick_name_connot_be_empty));
            return false;
        }
        if (newNick.equals(user.getMuserNick())) {
            mEtUpdateUserNick.requestFocus();
            mEtUpdateUserNick.setError(getString(R.string.update_nick_fail_unmodify));
            return false;
        }
        return true;
    }
}
