package com.song.tasty.common.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @date : 2019-07-22 18:15
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState, getLayoutResId());
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, int layoutResId);

    protected void initView() {

    }

    protected void initData() {

    }
}



