package com.song.tasty.module.video.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.video.R;
import com.song.tasty.module.video.databinding.VideoFragmentBinding;
import com.song.tasty.module.video.mvvm.viewmodel.VideoViewModel;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class VideoFragment extends BaseAppFragment<VideoFragmentBinding, VideoViewModel> {

    @Override
    public int initVariableId() {
        return 0;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.video_fragment;
    }


    public static VideoFragment getInstance() {
        VideoFragment fragment = new VideoFragment();

        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initObserve() {

    }
}
