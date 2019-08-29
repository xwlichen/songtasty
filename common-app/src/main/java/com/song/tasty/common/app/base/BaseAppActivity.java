package com.song.tasty.common.app.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.hjq.toast.ToastUtils;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.dialog.SMUITipDialog;
import com.song.tasty.common.app.R;
import com.song.tasty.common.core.base.BaseMvvmActivity;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.utils.SmartUtils;

/**
 * @author lichen
 * @date ：2019-07-22 22:10
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseAppActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseMvvmActivity<V, VM> {

    protected SMUITipDialog smuiTipDialog;

    @Override
    public void createView(int layoutResId, @Nullable Bundle savedInstanceState) {
        SMUIStatusBarHelper.translucent(this);
        SMUIStatusBarHelper.setStatusBarLightMode(this);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
    public void launchActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        SmartUtils.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
