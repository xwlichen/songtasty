package com.song.tasty.module.home.mvvm.ui;

import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.home.R;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeFragment extends BaseAppFragment {

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }


    public static HomeFragment getInstance() {
//        Bundle bundle = new Bundle();
//        bundle.putString("subject_id", subject_id);
//        bundle.putString("class_id", class_id);
        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(bundle);
        //        subject_id = getArguments().getString("subject_id");

        return fragment;

    }


}
