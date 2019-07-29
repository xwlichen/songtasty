package com.song.tasty.common.app.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.hjq.toast.ToastUtils;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.song.tasty.common.core.base.BaseMvvmActivity;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.utils.Preconditions;
import com.song.tasty.common.core.utils.SmartUtils;

/**
 * @author lichen
 * @date ：2019-07-22 22:10
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseAppActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseMvvmActivity<V, VM> {


    @Override
    protected void createView(int layoutResId, @Nullable Bundle savedInstanceState) {
        SMUIStatusBarHelper.translucent(this);
//        SMUIStatusBarHelper.setStatusBarLightMode(this);
        super.createView(layoutResId, savedInstanceState);
    }

    @Override
    public void toast(@Nullable String msg) {
        ToastUtils.show(msg);
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
        Preconditions.checkNotNull(intent);
        SmartUtils.startActivity(intent);
    }
}
