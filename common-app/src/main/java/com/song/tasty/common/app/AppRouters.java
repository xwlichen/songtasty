package com.song.tasty.common.app;

/**
 * @author lichen
 * @date ：2019-08-24 18:57
 * @email : 196003945@qq.com
 * @description :
 */
public interface AppRouters {


    //---------------------------------------------首页模块相关开放api------------------------------------------------------

    /**
     * HomeComponent 对HomeFragment的开放Api
     * (@link com.song.tasty.module.home.components.HomeComponent for HomeFragment )
     */
    String HOME_COMP_MAIN = "module.home.component.main";
    //get HomeFragment instance
    String HOME_COMP_MAIN_ACTION_GET_HOMEFRAGMENT = "getHomeFragment";

    //---------------------------------------------首页模块相关开放api------------------------------------------------------


    //---------------------------------------------云村模块相关开放api------------------------------------------------------
    //---------------------------------------------云村模块相关开放api------------------------------------------------------


    //---------------------------------------------音视模块相关开放api------------------------------------------------------

    /**
     * VideoComponent 对VideoFragment的开放Api
     * (@link com.song.tasty.module.video.components.VideoComponent for VideoFragment )
     */
    String VIDEO_COMP_MAIN = "module.video.component.main";
    //get VideoFragment instance
    String VIDEO_COMP_MAIN_ACTION_GET_VIDEOFRAGMENT = "getVideoFragment";
    //---------------------------------------------音视模块相关开放api------------------------------------------------------


    //---------------------------------------------我的模块相关开放api------------------------------------------------------


    /**
     * MineComponent 对MineFragment的开放Api
     * (@link com.song.tasty.module.mine.components.MineComponent for MineFragment )
     */
    String MINE_COMP_MAIN = "module.mine.components.main";
    //get MineFragment instance
    String MINE_COMP_MAIN_ACTION_GET_MINEFRAGMENT = "getMineFragment";


    //---------------------------------------------我的模块相关开放api------------------------------------------------------

}
