package com.example.apple.fulicenter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.I;
import com.example.apple.fulicenter.ui.view.MFGT;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/4/16.
 */

public class OrderActivity extends AppCompatActivity {
    int orderPrice;

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.ed_order_name)
    EditText mEdOrderName;
    @BindView(R.id.ed_order_phone)
    EditText mEdOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner mSpinOrderProvince;
    @BindView(R.id.ed_order_street)
    EditText mEdOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        orderPrice = getIntent().getIntExtra(I.ORDER_BUY_PRICE,0);
        initView();
    }

    private void initView() {
        mTvCommonTitle.setText("填写收件人信息");
        mTvOrderPrice.setText("合计：￥"+orderPrice);
    }

    @OnClick(R.id.backClickArea)
    public void backArea(){
        MFGT.finish(OrderActivity.this);
    }

    @OnClick(R.id.tv_order_buy)
    public void orderCommit(){

        String receiveName = mEdOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)) {
            mEdOrderName.requestFocus();
            mEdOrderName.setError("收件人姓名不能为空");
            return;
        }

        String phone = mEdOrderPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mEdOrderPhone.requestFocus();
            mEdOrderPhone.setError("手机号码不能为空");
            return;
        }
        if (!phone.matches("1[\\d]{10}")) {
            mEdOrderPhone.requestFocus();
            mEdOrderPhone.setError("手机号码格式错误");
        }

        String area = mSpinOrderProvince.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(this, "收货地区不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        String address = mEdOrderStreet.getText().toString();
        if (TextUtils.isEmpty(address)) {
            mEdOrderStreet.requestFocus();
            mEdOrderStreet.setError("详细地址不能为空");
            return;
        }
    }
}
