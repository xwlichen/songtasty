package com.song.tasty.common.app.base;

import android.content.Intent;
import android.os.Bundle;

import com.hjq.toast.ToastUtils;
import com.song.tasty.common.app.R;
import com.song.tasty.common.core.base.BaseMvvmFragment;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.utils.SmartUtils;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

/**
 * @date : 2019-08-22 11:23
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class BaseAppFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseMvvmFragment<V, VM> {

    protected static final TransitionConfig SLIDE_TRANSITION_CONFIG = new TransitionConfig(
            R.anim.slide_in_right, R.anim.slide_out_left,
            R.anim.slide_in_left, R.anim.slide_out_right);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        SMUIStatusBarHelper.translucent(getActivity());
//        SMUIStatusBarHelper.setStatusBarLightMode(getActivity());
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        SmartUtils.startActivity(intent);
    }


    public static final class TransitionConfig {
        public final int enter;
        public final int exit;
        public final int popenter;
        public final int popout;

        public TransitionConfig(int enter, int popout) {
            this(enter, 0, 0, popout);
        }

        public TransitionConfig(int enter, int exit, int popenter, int popout) {
            this.enter = enter;
            this.exit = exit;
            this.popenter = popenter;
            this.popout = popout;
        }
    }


}
