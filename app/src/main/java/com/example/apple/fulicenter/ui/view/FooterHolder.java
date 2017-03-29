package com.example.apple.fulicenter.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.apple.fulicenter.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/3/30.
 */

public class FooterHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvFooter)
    TextView mTvFooter;

    public FooterHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setFooterString(String footerString) {
        mTvFooter.setText(footerString);
    }
    public void setFooterString(int footerString) {
        mTvFooter.setText(footerString);
    }

}