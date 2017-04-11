package com.example.apple.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.application.FuLiCenterApplication;
import com.example.apple.fulicenter.model.bean.User;
import com.example.apple.fulicenter.model.utils.ImageLoader;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.ui.view.MFGT;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/4/7.
 */

public class PersonalCenterFragment extends Fragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    Unbinder unbinder;
    User mUser;
    @BindView(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /* if (mUser == null) {
            MFGT.gotoLoginActivity(getActivity());
        } else {
            showUserInfo();
            initOrderList();
        }*/
        initOrderList();
        initData();


    }

    // 重新登录的时候，更新个人中心的账号信息，防止显示上个登录账号的信息
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mUser = FuLiCenterApplication.getCurrentUser();
        if (mUser != null) {
            showUserInfo();

        }
    }

    private void showUserInfo() {
        mTvUserName.setText(mUser.getMuserNick());
//        ImageLoader.downloadImg(getContext(), mIvUserAvatar, mUser.getAvatar());
        ImageLoader.setAvatar(mUser.getAvatar(), getContext(),mIvUserAvatar);
        L.e(TAG, "mUser.getAvatar = " + mUser.getAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> order1 = new HashMap<>();
        order1.put("order", R.drawable.order_list1);
        data.add(order1);

        HashMap<String, Object> order2 = new HashMap<>();
        order2.put("order", R.drawable.order_list2);
        data.add(order2);

        HashMap<String, Object> order3 = new HashMap<>();
        order3.put("order", R.drawable.order_list3);
        data.add(order3);

        HashMap<String, Object> order4 = new HashMap<>();
        order4.put("order", R.drawable.order_list4);
        data.add(order4);

        HashMap<String, Object> order5 = new HashMap<>();
        order5.put("order", R.drawable.order_list5);
        data.add(order5);

        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.iv_order});
        mCenterUserOrderLis.setAdapter(adapter);

    }

    @OnClick({R.id.center_top, R.id.center_user_info})
    public void goSettingsActivity(View view) {
        MFGT.gotoSettingsActivity(getActivity());
    }
}

