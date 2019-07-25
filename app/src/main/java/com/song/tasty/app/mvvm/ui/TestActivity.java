package com.song.tasty.app.mvvm.ui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.song.tasty.app.R;
import com.song.tasty.app.databinding.ActivityTestBinding;
import com.song.tasty.app.mvvm.viewmodel.TestViewModel;
import com.song.tasty.common.core.base.BaseMvvmActivity;

/**
 * @author lichen
 * @date ：2019-07-24 22:12
 * @email : 196003945@qq.com
 * @description :
 */
public class TestActivity extends BaseMvvmActivity<ActivityTestBinding, TestViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void toast(@Nullable String msg) {
//        ActivityTestBinding mBinding;
//        mBinding.

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showNoNetWork() {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }
}
