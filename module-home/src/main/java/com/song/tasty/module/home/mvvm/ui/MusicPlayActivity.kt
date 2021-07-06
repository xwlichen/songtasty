package com.song.tasty.module.home.mvvm.ui

import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.module.home.R
import com.song.tasty.module.home.mvvm.viewmodel.MusicPlayViewModel

/**
 * @date : 2020-01-13 14:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicPlayActivity : BaseAppActivity<MusicPlayViewModel>() {

    override fun getLayoutResId(): Int {
        return R.layout.home_activity_music_play;
    }


    override fun initView() {
//        if (!AppUtils.isServiceRunning(this, "MusicService")) {
//            startService(Intent(this, MusicService::class.java))
//        }
    }
}