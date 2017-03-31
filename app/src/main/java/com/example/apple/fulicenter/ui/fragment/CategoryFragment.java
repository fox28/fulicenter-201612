package com.example.apple.fulicenter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.apple.fulicenter.R;
import com.example.apple.fulicenter.model.bean.CategoryChildBean;
import com.example.apple.fulicenter.model.bean.CategoryGroupBean;
import com.example.apple.fulicenter.model.net.CategoryModel;
import com.example.apple.fulicenter.model.net.ICategoryModel;
import com.example.apple.fulicenter.model.net.OnCompleteListener;
import com.example.apple.fulicenter.model.utils.L;
import com.example.apple.fulicenter.model.utils.ResultUtils;
import com.example.apple.fulicenter.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by apple on 2017/3/31.
 */
public class CategoryFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();

    List<CategoryGroupBean> groupList;
    List<List<CategoryChildBean>> childList;
    CategoryAdapter mAdapter;
    ICategoryModel mModel;
    int loadIndex = 0;

    @BindView(R.id.elv)
    ExpandableListView mElv;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new CategoryModel();
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        loadGroupData();
        mAdapter = new CategoryAdapter(getActivity());
        mElv.setAdapter(mAdapter);


    }

    private void loadGroupData() {
        mModel.loadGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    ArrayList<CategoryGroupBean> list = ResultUtils.array2List(result);

                    groupList.clear();// 清空groupList，每次都是下载新的内容
                    groupList.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        childList.add(new ArrayList<CategoryChildBean>());
                        loadChildData(list.get(i).getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void loadChildData(int parentId, final int index) {
        mModel.loadChildData(getContext(), parentId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                loadIndex++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ResultUtils.array2List(result);
                    childList.set(index,list);
                }
                if (loadIndex == groupList.size()) {
                    mAdapter.initData(groupList, childList);

                    L.e(TAG, "标记1：load and data ... ");
                }

            }

            @Override
            public void onError(String error) {
                loadIndex++;
                L.e(TAG, "标记2：loadChildData,"+error);

                if (loadIndex == groupList.size()) {
                    mAdapter.initData(groupList, childList);

                    L.e(TAG, "标记3：load and data ... ");
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
