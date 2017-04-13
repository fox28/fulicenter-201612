package com.example.apple.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.net.CartModel;
import com.example.apple.fulicenter.model.net.ICartModel;

/**
 * Created by apple on 2017/4/13.
 */

public class CartFragment extends Fragment {

    ICartModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new CartModel();
    }
}
