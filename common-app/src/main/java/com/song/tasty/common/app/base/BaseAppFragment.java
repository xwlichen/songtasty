package com.song.tasty.common.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hjq.toast.ToastUtils;
import com.smart.ui.LogUtils;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.song.tasty.common.app.R;
import com.song.tasty.common.core.base.BaseMvvmFragment;
import com.song.tasty.common.core.base.BaseViewModel;
import com.song.tasty.common.core.utils.SmartUtils;

/**
 * @date : 2019-08-22 11:23
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class BaseAppFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseMvvmFragment<V, VM> {
    public static final String TAG = BaseAppFragment.class.getSimpleName();


    protected static final TransitionConfig SLIDE_TRANSITION_CONFIG = new TransitionConfig(
            R.anim.slide_in_right, R.anim.slide_out_left,
            R.anim.slide_in_left, R.anim.slide_out_right);

    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
    public static final int RESULT_OK = Activity.RESULT_OK;
    private static final int NO_REQUEST_CODE = 0;
    public static final int ANIMATION_ENTER_STATUS_NOT_START = -1;
    public static final int ANIMATION_ENTER_STATUS_STARTED = 0;
    public static final int ANIMATION_ENTER_STATUS_END = 1;

    private int sourceRequestCode = NO_REQUEST_CODE;
    private Intent resultData = null;
    private int resultCode = RESULT_CANCELED;

    private BaseAppFragment childTargetFragment;
    private int enterAnimationStatus = ANIMATION_ENTER_STATUS_NOT_START;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SMUIStatusBarHelper.translucent(getActivity());
        SMUIStatusBarHelper.setStatusBarLightMode(getActivity());
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        enterAnimationStatus = ANIMATION_ENTER_STATUS_NOT_START;
    }

    public abstract void initObserve();

    @Override
    public void initData() {
        initObserve();
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
    public void showError() {

    }

    @Override
    public void launchActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        SmartUtils.startActivity(intent);
    }


    @Override
    public void finish() {
        popBackStack();
    }


    /**
     * simulate the behavior of startActivityForResult/onActivityResult:
     * 1. Jump fragment1 to fragment2 via startActivityForResult(fragment2, requestCode)
     * 2. Pass data from fragment2 to fragment1 via setFragmentResult(RESULT_OK, data)
     * 3. Get data in fragment1 through onFragmentResult(requestCode, resultCode, data)
     *
     * @param fragment    target fragment
     * @param requestCode request code
     */
    public void startFragmentForResult(BaseAppFragment fragment, int requestCode) {
        if (!checkStateLoss("startFragmentForResult")) {
            return;
        }
        if (requestCode == NO_REQUEST_CODE) {
            throw new RuntimeException("requestCode can not be " + NO_REQUEST_CODE);
        }
        BaseAppFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            FragmentManager targetFragmentManager = baseFragmentActivity.getSupportFragmentManager();
            Fragment topFragment = this;
            Fragment parent = this;
            while (parent != null) {
                topFragment = parent;
                if (parent.getFragmentManager() == targetFragmentManager) {
                    break;
                }
                parent = parent.getParentFragment();
            }
            sourceRequestCode = requestCode;
            if (topFragment == this) {
                childTargetFragment = null;
                fragment.setTargetFragment(this, requestCode);
            } else if (topFragment.getFragmentManager() == targetFragmentManager) {
                BaseAppFragment baseAppFragment = (BaseAppFragment) topFragment;
                baseAppFragment.sourceRequestCode = requestCode;
                baseAppFragment.childTargetFragment = this;
                fragment.setTargetFragment(baseAppFragment, requestCode);
            } else {
                throw new RuntimeException("fragment manager not matched");
            }
            startFragment(fragment);
        }
    }

    protected void startFragment(BaseAppFragment fragment) {
        if (!checkStateLoss("startFragment")) {
            return;
        }
        BaseAppFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity()) {
                baseFragmentActivity.startFragment(fragment);
            } else {
                Log.e(TAG, "fragment not attached:" + this);
            }
        } else {
            Log.e(TAG, "startFragment null:" + this);
        }
    }


    public void setFragmentResult(int resultCode, Intent data) {
        int targetRequestCode = getTargetRequestCode();
        if (targetRequestCode == 0) {
            LogUtils.w(TAG, "call setFragmentResult, but not requestCode exists");
            return;
        }
        Fragment fragment = getTargetFragment();
        if (!(fragment instanceof BaseAppFragment)) {
            return;
        }
        BaseAppFragment targetFragment = (BaseAppFragment) fragment;

        if (targetFragment.sourceRequestCode == targetRequestCode) {
            if (targetFragment.childTargetFragment != null) {
                targetFragment = targetFragment.childTargetFragment;
            }
            targetFragment.resultCode = resultCode;
            targetFragment.resultData = data;
        }
    }


    protected void startFragmentAndDestroyCurrent(BaseAppFragment fragment) {
        startFragmentAndDestroyCurrent(fragment, true);
    }


    /**
     * see {@link BaseAppFragmentActivity#startFragmentAndDestroyCurrent(BaseAppFragment, boolean)}
     *
     * @param fragment                      new fragment to start
     * @param useNewTransitionConfigWhenPop
     */
    protected void startFragmentAndDestroyCurrent(BaseAppFragment fragment, boolean useNewTransitionConfigWhenPop) {
        if (!checkStateLoss("startFragmentAndDestroyCurrent")) {
            return;
        }
        if (getTargetFragment() != null) {
            // transfer target fragment
            fragment.setTargetFragment(getTargetFragment(), getTargetRequestCode());
            setTargetFragment(null, 0);
        }
        BaseAppFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity()) {
                baseFragmentActivity.startFragmentAndDestroyCurrent(fragment, useNewTransitionConfigWhenPop);
            } else {
                Log.e("BaseAppFragment", "fragment not attached:" + this);
            }
        } else {
            Log.e("BaseAppFragment", "startFragment null:" + this);
        }
    }


    private boolean checkStateLoss(String logName) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            LogUtils.d(TAG, logName + " can not be invoked because fragmentManager == null");
            return false;
        }
        if (fragmentManager.isStateSaved()) {
            LogUtils.d(TAG, logName + " can not be invoked after onSaveInstanceState");
            return false;
        }
        return true;
    }

    public final BaseAppFragmentActivity getBaseFragmentActivity() {
        return (BaseAppFragmentActivity) getActivity();
    }


    protected void popBackStack() {
        if (checkPopBack()) {
            getBaseFragmentActivity().popBackStack();
        }
    }

    private boolean checkPopBack() {
        if (!isResumed() || enterAnimationStatus != ANIMATION_ENTER_STATUS_END) {
            return false;
        }
        return checkStateLoss("popBackStack");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }


    /**
     * When finishing to pop back last fragment, let activity have a chance to do something
     * like start a new fragment
     *
     * @return BaseAppFragment to start a new fragment or Intent to start a new Activity
     */
    @SuppressWarnings("SameReturnValue")
    public Object onLastFragmentFinish() {
        return null;
    }


    /**
     * restore sub window(e.g dialog) when drag back to previous activity
     *
     * @return
     */
    protected boolean restoreSubWindowWhenDragBack() {
        return true;
    }

    /**
     * Fragment Transition Controller
     */
    public TransitionConfig onFetchTransitionConfig() {
        return SLIDE_TRANSITION_CONFIG;
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
