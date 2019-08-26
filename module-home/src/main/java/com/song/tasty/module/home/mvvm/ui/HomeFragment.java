package com.song.tasty.module.home.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smart.ui.LogUtils;
import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.databinding.HomeFragmentBinding;
import com.song.tasty.module.home.mvvm.viewmodel.HomeViewModel;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeFragment extends BaseAppFragment<HomeFragmentBinding, HomeViewModel> {

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }


    public static HomeFragment getInstance() {
//        Bundle bundle = new Bundle();
//        bundle.putString("subject_id", subject_id);
//        bundle.putString("class_id", class_id);
        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(bundle);
        //        subject_id = getArguments().getString("subject_id");

        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("xw", "HomeFragment onCreateView");
        setName("HomeFragment");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
