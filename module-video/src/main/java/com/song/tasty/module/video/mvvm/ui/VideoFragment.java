package com.song.tasty.module.video.mvvm.ui;

import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.video.R;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class VideoFragment extends BaseAppFragment {

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.video_fragment;
    }


    public static VideoFragment getInstance() {
//        Bundle bundle = new Bundle();
//        bundle.putString("subject_id", subject_id);
//        bundle.putString("class_id", class_id);
        VideoFragment fragment = new VideoFragment();
//        fragment.setArguments(bundle);
        //        subject_id = getArguments().getString("subject_id");

        return fragment;

    }


}
