package com.song.tasty.common.core.base;

import android.os.Bundle;
import android.view.View;

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

    private static final String TAG = BaseFragment.class.getSimpleName();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewCreate();

        initTitle();
        initView();
        initData();
    }

    protected abstract int getLayoutResId();

    protected void initViewCreate() {
    }


    protected void initTitle() {
    }


    protected void initView() {
    }

    protected void initData() {
    }


    public boolean isAttachedToActivity() {
        return !isRemoving();
    }


}



