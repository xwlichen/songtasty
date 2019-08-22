package com.song.tasty.common.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.song.tasty.common.core.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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


    @Override
    public void onResume() {
        checkLatestVisitRecord();
        super.onResume();
    }

    private void checkLatestVisitRecord() {
//        Class<? extends BaseFragment> cls = getClass();
//        Activity activity = getActivity();
//        if(getParentFragment() != null || !(activity instanceof FragmentActivity)){
//            return;
//        }
//        if (!cls.isAnnotationPresent(LatestVisitRecord.class)) {
//            QMUILatestVisit.getInstance(getContext()).clearFragmentLatestVisitRecord();
//            return;
//        }
//        if (!activity.getClass().isAnnotationPresent(LatestVisitRecord.class)) {
//            throw new RuntimeException(String.format("Can not perform LatestVisitRecord, " +
//                    "%s must be annotated by LatestVisitRecord", activity.getClass().getSimpleName()));
//        }
//        if (activity.getClass().getAnnotation(DefaultFirstFragment.class) != null) {
//            QMUILatestVisit.getInstance(getContext()).performLatestVisitRecord(this);
//        } else {
//            QMUIFragmentActivity qActivity = (QMUIFragmentActivity) activity;
//            int id = FirstFragmentFinders.getInstance().get(qActivity.getClass()).getIdByFragmentClass(cls);
//            if (id == FirstFragmentFinder.NO_ID) {
//                throw new RuntimeException(String.format("Can not perform LatestVisitRecord, " +
//                                "%s must be annotated by FirstFragments which contains %s",
//                        activity.getClass().getSimpleName(), cls.getSimpleName()));
//            }
//            QMUILatestVisit.getInstance(getContext()).performLatestVisitRecord(this);
//        }
    }


    protected void startFragmentAndDestroyCurrent(BaseFragment fragment) {
        startFragmentAndDestroyCurrent(fragment, true);
    }


    protected void startFragmentAndDestroyCurrent(BaseFragment fragment, boolean useNewTransitionConfigWhenPop) {
        if (!checkStateLoss("startFragmentAndDestroyCurrent")) {
            return;
        }
        if (getTargetFragment() != null) {
            // transfer target fragment
            fragment.setTargetFragment(getTargetFragment(), getTargetRequestCode());
            setTargetFragment(null, 0);
        }
        QMUIFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity()) {
                ViewCompat.setTranslationZ(mCacheSwipeBackLayout, --mBackStackIndex);
                baseFragmentActivity.startFragmentAndDestroyCurrent(fragment, useNewTransitionConfigWhenPop);
            } else {
                Log.e("QMUIFragment", "fragment not attached:" + this);
            }
        } else {
            Log.e("QMUIFragment", "startFragment null:" + this);
        }
    }

    protected void startFragment(QMUIFragment fragment) {
        if (!checkStateLoss("startFragment")) {
            return;
        }
        QMUIFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity()) {
                baseFragmentActivity.startFragment(fragment);
            } else {
                Log.e("QMUIFragment", "fragment not attached:" + this);
            }
        } else {
            Log.e("QMUIFragment", "startFragment null:" + this);
        }
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
    public void startFragmentForResult(QMUIFragment fragment, int requestCode) {
        if (!checkStateLoss("startFragmentForResult")) {
            return;
        }
        if (requestCode == NO_REQUEST_CODE) {
            throw new RuntimeException("requestCode can not be " + NO_REQUEST_CODE);
        }
        QMUIFragmentActivity baseFragmentActivity = this.getBaseFragmentActivity();
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
            mSourceRequestCode = requestCode;
            if (topFragment == this) {
                mChildTargetFragment = null;
                fragment.setTargetFragment(this, requestCode);
            } else if (topFragment.getFragmentManager() == targetFragmentManager) {
                QMUIFragment qmuiFragment = (QMUIFragment) topFragment;
                qmuiFragment.mSourceRequestCode = requestCode;
                qmuiFragment.mChildTargetFragment = this;
                fragment.setTargetFragment(qmuiFragment, requestCode);
            } else {
                throw new RuntimeException("fragment manager not matched");
            }
            startFragment(fragment);
        }
    }


    public void setFragmentResult(int resultCode, Intent data) {
        int targetRequestCode = getTargetRequestCode();
        if (targetRequestCode == 0) {
            QMUILog.w(TAG, "call setFragmentResult, but not requestCode exists");
            return;
        }
        Fragment fragment = getTargetFragment();
        if (!(fragment instanceof QMUIFragment)) {
            return;
        }
        QMUIFragment targetFragment = (QMUIFragment) fragment;

        if (targetFragment.mSourceRequestCode == targetRequestCode) {
            if (targetFragment.mChildTargetFragment != null) {
                targetFragment = targetFragment.mChildTargetFragment;
            }
            targetFragment.mResultCode = resultCode;
            targetFragment.mResultData = data;
        }
    }


    private boolean checkStateLoss(String logName) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            LogUtils.d(TAG, logName + " can not be invoked because fragmentManager == null");

            return false;
        }
        if (fragmentManager.isStateSaved()) {
            QMUILog.d(TAG, logName + " can not be invoked after onSaveInstanceState");
            return false;
        }
        return true;
    }



}



