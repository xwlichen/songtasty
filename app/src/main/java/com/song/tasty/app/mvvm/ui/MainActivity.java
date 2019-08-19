package com.song.tasty.app.mvvm.ui;

import android.graphics.Color;

import com.smart.ui.widget.bottomnav.lottie.NavItem;
import com.smart.ui.widget.bottomnav.lottie.NavItemBuilder;
import com.song.tasty.app.R;
import com.song.tasty.app.databinding.AppActivityMainBinding;
import com.song.tasty.app.mvvm.viewmodel.MainViewModel;
import com.song.tasty.common.app.BR;
import com.song.tasty.common.app.base.BaseAppActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseAppActivity<AppActivityMainBinding, MainViewModel> {


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
//        list.add(item4);

//        bottomNav.setCallback(this);
        binding.bottomNav.setNavItemList(list);


    }


}
