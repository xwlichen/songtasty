package com.song.tasty.common.app.music

import com.song.tasty.common.app.music.bean.MusicBean
import com.song.tasty.common.app.music.emums.PlayModel

/**
 * @date : 2020-01-08 11:09
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicManager private constructor() {

    var musicList: MutableList<MusicBean> = ArrayList()
    var playModel: PlayModel = PlayModel.CIRCLE
    var currentIndex: Int = 0;

    /**
     * PLAYER_STATUS_PREPARED 1 //准备完成
     * PLAYER_STATUS_PLAYING 2  //正在播放
     * PLAYER_STATUS_PAUSED 3 //暂停
     * PLAYER_STATUS_LOADING 4 //正在加载
     * PLAYER_STATUS_STOP 5 //停止
     */
    var currentStatus: Int = 0;


    //静态方法、变量
    companion object {
        val instance: MusicManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { MusicManager() }

    }

    fun setDataList(list: MutableList<MusicBean>) {

    }

    fun playMusic(bean: MusicBean) {
        musicList.clear()
        musicList.add(bean)
        currentIndex = 0;


    }

    fun play() {

    }

    fun pause() {

    }

    fun resume() {

    }


    /**
     * 单位：s
     */
    fun seek(second: Int) {

    }

    fun pre() {

    }

    fun next() {

    }

    fun stop() {

    }


}