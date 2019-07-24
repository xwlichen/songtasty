package com.song.tasty.main.mvvm.ui;

import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.main.BR;
import com.song.tasty.main.R;
import com.song.tasty.main.databinding.ActivityMainBinding;
import com.song.tasty.main.mvvm.viewmodel.TestViewModel;


public class MainActivity extends BaseAppActivity<ActivityMainBinding, TestViewModel> {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    protected void initView() {
        super.initView();
    }
}
