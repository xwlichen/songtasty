package com.song.tasty.app.mvvm.ui;

import com.song.tasty.app.BR;
import com.song.tasty.app.R;
import com.song.tasty.app.databinding.ActivityMainBinding;
import com.song.tasty.app.mvvm.viewmodel.MainViewModel;
import com.song.tasty.common.app.base.BaseAppActivity;


public class MainActivity extends BaseAppActivity<ActivityMainBinding, MainViewModel> {


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


    }


}
