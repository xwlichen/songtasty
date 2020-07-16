package com.song.tasty.app.mvvm.ui;

import android.graphics.Color;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.hjq.toast.ToastUtils;
import com.smart.ui.widget.bottomnav.lottie.ILottieBottomNavViewCallback;
import com.smart.ui.widget.bottomnav.lottie.NavItem;
import com.smart.ui.widget.bottomnav.lottie.NavItemBuilder;
import com.song.tasty.app.BR;
import com.song.tasty.app.R;
import com.song.tasty.app.databinding.AppActivityMainBinding;
import com.song.tasty.app.mvvm.viewmodel.MainViewModel;
import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.common.core.AppManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;

import static com.song.tasty.common.app.AppRouters.GET_FRAGMENT;
import static com.song.tasty.common.app.AppRouters.HOME_COMP_MAIN;
import static com.song.tasty.common.app.AppRouters.MINE_COMP_MAIN;
import static com.song.tasty.common.app.AppRouters.VIDEO_COMP_MAIN;


public class MainActivity extends BaseAppActivity<AppActivityMainBinding, MainViewModel> {


    private HashMap<String, Fragment> fragments = new HashMap<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.app_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initView() {
        initNav();

        CC.obtainBuilder(HOME_COMP_MAIN)
                .setActionName(GET_FRAGMENT)
                .build()
                .callAsyncCallbackOnMainThread(fragmentCallback);

//        EventBus.getDefault().register(this);
//        EventBus.getDefault().post("");


    }

    private void initNav() {
        NavItem item1 = new NavItem("发现",
                Color.BLACK,
                Color.BLACK,
                "lotties/tab_select_discovery_anim.json",
                R.mipmap.ic_tab_discovery_normal,
                NavItem.Source.Assets,
                100,
                false,
                false,
                null
        );

        NavItem item2 = NavItemBuilder.createFrom(item1)
                .navTitle("音视")
                .selectedLottieName("lotties/tab_select_live_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_live_normal)
                .build();

        NavItem item3 = NavItemBuilder.createFrom(item1)
                .navTitle("我的")
                .selectedLottieName("lotties/tab_select_me_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_me_normal)
                .build();


        List<NavItem> list;
        list = new ArrayList<>(3);
        list.add(item1);
        list.add(item2);
        list.add(item3);

        binding.bottomNav.setCallback(new ILottieBottomNavViewCallback() {
            @Override
            public void onNavSelected(int oldIndex, int newIndex, NavItem menuItem) {
                changeFragment(newIndex);

//                startActivity(
//                        FlutterActivity
//                                .withNewEngine()
//                                .initialRoute("route1")
//                                .build(MainActivity.this));
            }

            @Override
            public void onAnimationStart(int index, NavItem menuItem) {

            }

            @Override
            public void onAnimationEnd(int index, NavItem menuItem) {

            }

            @Override
            public void onAnimationCancel(int index, NavItem menuItem) {

            }
        });
        binding.bottomNav.setNavItemList(list);
    }


    IComponentCallback fragmentCallback = new IComponentCallback() {
        @Override
        public void onResult(CC cc, CCResult result) {
            if (result.isSuccess()) {
                String tag = cc.getComponentName() + cc.getActionName();
                Fragment fragment = fragments.get(tag);
                if (fragment == null) {
                    Fragment newFragment = result.getDataItemWithNoKey();
                    fragments.put(tag, newFragment);
                    showFragment(tag, newFragment, true);
                } else {
                    showFragment(tag, fragment, false);
                }

            } else {
                ToastUtils.show("显示fragment失败");
            }
        }
    };

    private void showFragment(String tag, Fragment fragment, boolean isNew) {
        if (fragment != null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//            trans.setCustomAnimations(com.song.tasty.common.app.R.anim.slide_in_right, com.song.tasty.common.app.R.anim.slide_out_left);

            Iterator<Map.Entry<String, Fragment>> iterator = fragments.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Fragment> entry = iterator.next();
                if (entry.getValue() != fragment) {
                    trans.hide(entry.getValue());
                }
            }

            if (isNew) {
                trans.add(R.id.fragmentContainer, fragment, tag).show(fragment);
            } else {
                trans.show(fragment);
            }

//            trans.replace(R.id.fragmentContainer, fragment);
            trans.commitAllowingStateLoss();
        }
    }

    private void changeFragment(int posotion) {
        Fragment currentFragment;
        String tag;

        switch (posotion) {
            case 0:
                currentFragment = fragments.get(HOME_COMP_MAIN + GET_FRAGMENT);
                tag = GET_FRAGMENT;
                if (currentFragment == null) {
                    CC.obtainBuilder(HOME_COMP_MAIN)
                            .setActionName(tag)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback);
                } else {
                    showFragment(tag, currentFragment, false);
                }
                break;
            case 1:

                currentFragment = fragments.get(VIDEO_COMP_MAIN + GET_FRAGMENT);
                tag = GET_FRAGMENT;
                if (currentFragment == null) {
                    CC.obtainBuilder(VIDEO_COMP_MAIN)
                            .setActionName(tag)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback);
                } else {
                    showFragment(tag, currentFragment, false);
                }
                break;
            case 2:
                currentFragment = fragments.get(MINE_COMP_MAIN + GET_FRAGMENT);
                tag = GET_FRAGMENT;
                if (currentFragment == null) {
                    CC.obtainBuilder(MINE_COMP_MAIN)
                            .setActionName(GET_FRAGMENT)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback);
                } else {
                    showFragment(tag, currentFragment, false);
                }
                break;
            default:
                break;
        }

    }


}
