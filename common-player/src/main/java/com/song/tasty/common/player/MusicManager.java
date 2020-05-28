package com.song.tasty.common.player;

import com.hjq.toast.ToastUtils;
import com.smart.media.player.SmartMusicPlayer;
import com.smart.media.player.bean.InfoBean;
import com.smart.media.player.enums.InfoCode;
import com.smart.media.player.interf.IPlayer;
import com.song.tasty.common.player.bean.MusicBean;
import com.song.tasty.common.player.emums.PlayModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @date : 2020-01-14 11:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicManager {
    private static MusicManager instance;

    private SmartMusicPlayer player;
    private List<MusicBean> musicList;
    private PlayModel playModel;
    int currentIndex;

    /**
     * PLAYER_STATUS_PREPARED 1 //准备完成
     * PLAYER_STATUS_PLAYING 2  //正在播放
     * PLAYER_STATUS_PAUSED 3 //暂停
     * PLAYER_STATUS_LOADING 4 //正在加载
     * PLAYER_STATUS_STOP 5 //停止
     */
    int currentStatus;


    public static synchronized MusicManager getInstance() {
        if (instance == null) {
            synchronized (MusicManager.class) {
                instance = new MusicManager();
            }
        }
        return instance;
    }


    public MusicManager() {
        musicList = new ArrayList<>();
        initPlayer();
    }


    protected void initPlayer() {
        player = new SmartMusicPlayer();

        player.setOnPreparedListener(new IPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                player.start();
            }
        });

        player.setOnInfoListener(new IPlayer.OnInfoListener() {
            @Override
            public void onInfo(InfoBean data) {
                if (data == null) {
                    return;
                }
                InfoCode code = data.getCode();
                switch (code) {
                    case CURRENT_PROGRESS:
                        String time = data.getExtraValue() + "";
                        ToastUtils.show(time);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public void setDataList(ArrayList<MusicBean> list) {
        musicList.clear();
        musicList.addAll(list);
    }

    public void playMusic(MusicBean bean) {
        musicList.clear();
        musicList.add(bean);
        currentIndex = 0;
        play();

    }

    public void play() {
        if (musicList != null && musicList.size() > 0) {
            if (currentIndex < musicList.size()) {
                player.setDataSource(musicList.get(currentIndex).getUrl());
                player.prepare();
            }
        }


    }

    public void pause() {
        player.pause();

    }

    public void resume() {
        player.resume();

    }


    /**
     * 单位：s
     */
    public void seek(int second) {
        player.seek(second);

    }

    public void pre() {
        currentIndex -= 1;
        play();

    }

    public void next() {
        currentIndex += 1;
        play();


    }

    public void stop() {

        player.stop();

    }
}
