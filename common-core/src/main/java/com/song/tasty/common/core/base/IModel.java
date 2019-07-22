package com.song.tasty.common.core.base;

/**
 * @date : 2019-07-22 16:10
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface IModel {

    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();

}
