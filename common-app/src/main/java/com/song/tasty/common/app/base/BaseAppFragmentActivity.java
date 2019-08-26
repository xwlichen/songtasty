package com.song.tasty.common.app.base;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.song.tasty.common.app.utils.Utils;
import com.song.tasty.common.core.base.BaseViewModel;

import java.lang.reflect.Field;

/**
 * @author lichen
 * @date ：2019-08-26 21:22
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseAppFragmentActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseAppActivity<V, VM> {

    public static final String TAG = BaseAppFragmentActivity.class.getSimpleName();

    protected abstract int getContextViewId();


    @Override
    public void onBackPressed() {
        BaseAppFragment fragment = getCurrentFragment();
        if (fragment != null) {
            fragment.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BaseAppFragment fragment = getCurrentFragment();
        if (fragment != null && fragment.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        BaseAppFragment fragment = getCurrentFragment();
        if (fragment != null && fragment.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    public BaseAppFragment getCurrentFragment() {
        return (BaseAppFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }


    public int startFragmentAndDestroyCurrent(final BaseAppFragment fragment, final boolean useNewTransitionConfigWhenPop) {
        final BaseAppFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
        String tagName = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit,
                        transitionConfig.popenter, transitionConfig.popout)
                .replace(getContextViewId(), fragment, tagName);
        int index = transaction.commit();
        Utils.findAndModifyOpInBackStackRecord(fragmentManager, -1, new Utils.OpHandler() {
            @Override
            public boolean handle(Object op) {
                Field cmdField = null;
                try {
                    cmdField = Utils.getOpCmdField(op);
                    cmdField.setAccessible(true);
                    int cmd = (int) cmdField.get(op);
                    if (cmd == 1) {
                        if (useNewTransitionConfigWhenPop) {
                            Field popEnterAnimField = Utils.getOpPopEnterAnimField(op);
                            popEnterAnimField.setAccessible(true);
                            popEnterAnimField.set(op, transitionConfig.popenter);

                            Field popExitAnimField = Utils.getOpPopExitAnimField(op);
                            popExitAnimField.setAccessible(true);
                            popExitAnimField.set(op, transitionConfig.popout);
                        }

                        Field oldFragmentField = Utils.getOpFragmentField(op);
                        oldFragmentField.setAccessible(true);
                        Object fragmentObj = oldFragmentField.get(op);
                        oldFragmentField.set(op, fragment);
                        Field backStackNestField = Fragment.class.getDeclaredField("mBackStackNesting");
                        backStackNestField.setAccessible(true);
                        int oldFragmentBackStackNest = (int) backStackNestField.get(fragmentObj);
                        backStackNestField.set(fragment, oldFragmentBackStackNest);
                        backStackNestField.set(fragmentObj, --oldFragmentBackStackNest);
                        return true;
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean needReNameTag() {
                return true;
            }

            @Override
            public String newTagName() {
                return fragment.getClass().getSimpleName();
            }
        });
        return index;
    }

    public int startFragment(BaseAppFragment fragment) {
        Log.i(TAG, "startFragment");
        BaseAppFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
        String tagName = fragment.getClass().getSimpleName();
        return getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .replace(getContextViewId(), fragment, tagName)
                .addToBackStack(tagName)
                .commit();
    }

    /**
     * Exit the current Fragment。
     */
    public void popBackStack() {
        Log.i(TAG, "popBackStack: getSupportFragmentManager().getBackStackEntryCount() = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            BaseAppFragment fragment = getCurrentFragment();
            if (fragment == null) {
                finish();
                return;
            }
            BaseAppFragment.TransitionConfig transitionConfig = fragment.onFetchTransitionConfig();
            Object toExec = fragment.onLastFragmentFinish();
            if (toExec != null) {
                if (toExec instanceof BaseAppFragment) {
                    BaseAppFragment mFragment = (BaseAppFragment) toExec;
                    startFragment(mFragment);
                } else if (toExec instanceof Intent) {
                    Intent intent = (Intent) toExec;
                    startActivity(intent);
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
                    finish();
                } else {
                    throw new Error("can not handle the result in onLastFragmentFinish");
                }
            } else {
                finish();
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout);
            }
        } else {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    /**
     * pop back to a clazz type fragment
     * <p>
     * Assuming there is a back stack: Home -> List -> Detail. Perform popBackStack(Home.class),
     * Home is the current fragment
     * <p>
     * if the clazz type fragment doest not exist in back stack, this method is Equivalent
     * to popBackStack()
     *
     * @param clazz the type of fragment
     */
    public void popBackStack(Class<? extends BaseAppFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), 0);
    }

    /**
     * pop back to a non-clazz type Fragment
     *
     * @param clazz the type of fragment
     */
    public void popBackStackInclusive(Class<? extends BaseAppFragment> clazz) {
        getSupportFragmentManager().popBackStack(clazz.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
