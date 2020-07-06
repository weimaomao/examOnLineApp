package com.starcity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.starcity.utils.SpUtils;

/**
 * Created by
 */
public abstract class BaseFragment extends Fragment {
    public View view;

    /**
     * 子类数据初始化方法
     */
    protected abstract void initData();

    /**
     * 子类初始化id
     */
    protected abstract void initID();

    // 子类布局初始化
    public abstract int setContentView();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(setContentView(), container, false);
//        sp = getActivity().getSharedPreferences("BlueToothLocation",
//                Context.MODE_PRIVATE);

        SpUtils.setSharedPerference(getActivity());

        initID();
        initData();

        return view;
    }
}
