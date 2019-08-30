package com.song.tasty.module.home.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.home.BR;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.adapter.HomeTitleViewBinder;
import com.song.tasty.module.home.databinding.HomeFragmentBinding;
import com.song.tasty.module.home.entity.HomeResult;
import com.song.tasty.module.home.entity.HomeTitleBean;
import com.song.tasty.module.home.mvvm.viewmodel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeFragment extends BaseAppFragment<HomeFragmentBinding, HomeViewModel> {


    private MultiTypeAdapter adapter;
    private Items items;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }


    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void initView() {

        adapter = new MultiTypeAdapter();
        HomeTitleViewBinder homeTitleViewBinder;
        adapter.register(HomeTitleBean.class, new HomeTitleViewBinder());
        items = items;


        viewModel.successResult.observe(this, new Observer<HomeResult>() {
            @Override
            public void onChanged(HomeResult homeResult) {

            }
        });

    }

    @Override
    public void initData() {
        viewModel.getData();
    }


}
