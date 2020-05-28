package com.song.tasty.common.app.music

import com.hjq.toast.ToastUtils
import com.smart.media.player.SmartMusicPlayer
import com.smart.media.player.bean.InfoBean
import com.smart.media.player.enums.InfoCode
import com.smart.media.player.interf.IPlayer
import com.song.tasty.common.app.music.bean.MusicBean1
import com.song.tasty.common.app.music.emums.PlayModel1
import com.song.tasty.common.player.MusicNotification

/**
 * @date : 2020-01-08 11:09
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicManager1 {


    lateinit var musicPlayer: SmartMusicPlayer
    var musicList1: ArrayList<MusicBean1>
    var playModel1: PlayModel1 = PlayModel1.CIRCLE
    var currentIndex: Int = 0;

    var musicNotification: MusicNotification? = null

    /**
     * PLAYER_STATUS_PREPARED 1 //准备完成
     * PLAYER_STATUS_PLAYING 2  //正在播放
     * PLAYER_STATUS_PAUSED 3 //暂停
     * PLAYER_STATUS_LOADING 4 //正在加载
     * PLAYER_STATUS_STOP 5 //停止
     */
    var currentStatus: Int = 0;


    private constructor() {

        musicList1 = ArrayList()
        initPlayer()

    }

    //静态方法、变量
    companion object {
        val instance: MusicManager1 by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { MusicManager1() }

    }


    fun initPlayer() {
        musicPlayer = SmartMusicPlayer()


        musicPlayer.setOnPreparedListener(object : IPlayer.OnPreparedListener {
            override fun onPrepared() {
                musicPlayer.start()
            }

        })

        musicPlayer.setOnInfoListener(object : IPlayer.OnInfoListener {
            override fun onInfo(data: InfoBean?) {
                var code = data?.code ?: -1;
                when (code) {
                    InfoCode.CURRENT_PROGRESS -> {
                        var time = data?.extraValue.toString()
                        ToastUtils.show(time)
                    }
                }
            }
        })
    }


    fun setDataList(list: ArrayList<MusicBean1>) {
        musicList1.clear()
        musicList1.addAll(list)
    }

    fun playMusic(bean1: MusicBean1) {
        musicList1.clear()
        musicList1.add(bean1)
        currentIndex = 0;
        play()

    }

    fun play() {
        musicList1.isNotEmpty().let {
            if (currentIndex < musicList1.size) {
                musicPlayer.setDataSource(musicList1[currentIndex].url)
                musicPlayer.prepare()
            }

        }


    }

    fun pause() {
        musicPlayer.pause()

    }

    fun resume() {
        musicPlayer.resume()

    }


    /**
     * 单位：s
     */
    fun seek(second: Int) {
        musicPlayer.seek(second)

    }

    fun pre() {
        currentIndex -= 1
        musicList1.isNotEmpty().let {
            if (currentIndex < musicList1.size) {
                musicPlayer.next(musicList1[currentIndex].url)
            }

        }

    }

    fun next() {
        currentIndex + 1
        musicList1.isNotEmpty().let {
            if (currentIndex < musicList1.size) {
                musicPlayer.next(musicList1[currentIndex].url)
            }

        }

    }

    fun stop() {

        musicPlayer.stop()

    }


}