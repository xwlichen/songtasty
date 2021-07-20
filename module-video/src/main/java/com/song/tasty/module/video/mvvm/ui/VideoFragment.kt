package com.song.tasty.module.video.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.cc.core.component.CC
import com.song.tasty.common.app.AppRouters
import com.song.tasty.common.app.base.BaseAppFragment
import com.song.tasty.module.video.R
import com.song.tasty.module.video.mvvm.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.video_fragment.*

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
class VideoFragment : BaseAppFragment<VideoViewModel?>() {
    override fun getLayoutResId(): Int {
        return R.layout.video_fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initObserve() {}
    override fun initView() {
        super.initView()
        tvTip.setOnClickListener(View.OnClickListener {
            val cc = CC.obtainBuilder(AppRouters.HOME_COMP_SONGSHEETDETAIL)
                    .setActionName(AppRouters.START_ACTIVITY)
                    .build()
            cc.call()
        })
    }

    companion object {
        @JvmStatic
        val instance: VideoFragment
            get() = VideoFragment()
    }
}