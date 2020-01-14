package com.song.tasty.module.home.mvvm.ui

import android.content.Intent
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.common.app.music.MusicService1
import com.song.tasty.common.app.utils.AppUtils
import com.song.tasty.module.home.BR
import com.song.tasty.module.home.R
import com.song.tasty.module.home.databinding.HomeActivityMusicPlayBinding
import com.song.tasty.module.home.mvvm.viewmodel.MusicPlayViewModel

/**
 * @date : 2020-01-13 14:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicPlayActivity : BaseAppActivity<HomeActivityMusicPlayBinding, MusicPlayViewModel>() {

    override fun getLayoutResId(): Int {
        return R.layout.home_activity_music_play;
    }

    override fun initVariableId(): Int {
        return BR.viewModel;
    }

    override fun initView() {
        if (!AppUtils.isServiceRunning(this, "MusicService1")) {
            startService(Intent(this, MusicService1::class.java))
        }
    }
}