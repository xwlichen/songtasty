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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseAppActivity<AppActivityMainBinding, MainViewModel> {


    private Fragment fragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.app_activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    protected void initView() {
        initNav();

        CC.obtainBuilder("module.home")
                .setActionName("getHomeFragment")
                .build()
                .callAsyncCallbackOnMainThread(fragmentCallback);


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
                Fragment fragment = result.getDataItemWithNoKey();
                if (fragment != null) {
                    showFragment(fragment);
                }
            } else {
                ToastUtils.show("显示fragment失败");
            }
        }
    };

    private void showFragment(Fragment fragment) {
        if (fragment != null) {
            this.fragment = fragment;
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.setCustomAnimations(com.song.tasty.common.app.R.anim.slide_in_right, com.song.tasty.common.app.R.anim.slide_out_left);
            trans.replace(R.id.fragmentContainer, fragment);
            trans.commit();
        }
    }

    private void changeFragment(int posotion) {

        switch (posotion) {
            case 0:
                CC.obtainBuilder("module.home")
                        .setActionName("getHomeFragment")
                        .build()
                        .callAsyncCallbackOnMainThread(fragmentCallback);
                break;
            case 1:
                CC.obtainBuilder("module.video")
                        .setActionName("getVideoFragment")
                        .build()
                        .callAsyncCallbackOnMainThread(fragmentCallback);
                break;
            case 2:
                CC.obtainBuilder("module.mine")
                        .setActionName("getMineFragment")
                        .build()
                        .callAsyncCallbackOnMainThread(fragmentCallback);
                break;
            default:
                break;
        }

    }


}
