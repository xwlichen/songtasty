package com.song.tasty.common.app.music

import com.hjq.toast.ToastUtils
import com.smart.media.player.SmartMusicPlayer
import com.smart.media.player.bean.InfoBean
import com.smart.media.player.enums.InfoCode
import com.smart.media.player.interf.IPlayer
import com.song.tasty.common.app.music.bean.MusicBean
import com.song.tasty.common.app.music.emums.PlayModel

/**
 * @date : 2020-01-08 11:09
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicManager {


    lateinit var musicPlayer: SmartMusicPlayer
    var musicList: ArrayList<MusicBean>
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


    private constructor() {

        musicList = ArrayList()
        initPlayer()

    }

    //静态方法、变量
    companion object {
        val instance: MusicManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { MusicManager() }

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


    fun setDataList(list: ArrayList<MusicBean>) {
        musicList.clear()
        musicList.addAll(list)
    }

    fun playMusic(bean: MusicBean) {
        musicList.clear()
        musicList.add(bean)
        currentIndex = 0;
        play()

    }

    fun play() {
        musicList.isNotEmpty().let {
            if (currentIndex < musicList.size) {
                musicPlayer.setDataSource(musicList[currentIndex].url)
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
        musicList.isNotEmpty().let {
            if (currentIndex < musicList.size) {
                musicPlayer.next(musicList[currentIndex].url)
            }

        }

    }

    fun next() {
        currentIndex + 1
        musicList.isNotEmpty().let {
            if (currentIndex < musicList.size) {
                musicPlayer.next(musicList[currentIndex].url)
            }

        }

    }

    fun stop() {

        musicPlayer.stop()

    }


}