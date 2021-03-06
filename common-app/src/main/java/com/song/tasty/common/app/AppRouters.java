package com.song.tasty.common.app;

/**
 * @author lichen
 * @date ：2019-08-24 18:57
 * @email : 196003945@qq.com
 * @description :
 */
public interface AppRouters {

    /**
     * Compent 的一些通用操作
     */
    //get Fragment instance
    String GET_FRAGMENT = "getFragment";
    //open acitivty
    String START_ACTIVITY = "startActivity";


    //---------------------------------------------登录模块相关开放api------------------------------------------------------
    /**
     * LoginComponent 对LoginActivity的开放Api
     * (@link com.song.tasty.module.login.component.LoginComponent for LoginActivity )
     */
    String LOGIN_COMP_MAIN = "module.login.component.main";

    //---------------------------------------------登录模块相关开放api------------------------------------------------------





    //---------------------------------------------首页模块相关开放api------------------------------------------------------

    /**
     * HomeComponent 对 HomeFragment 的开放Api
     * (@link com.song.tasty.module.home.components.HomeComponent for HomeFragment )
     */
    String HOME_COMP_MAIN = "module.home.component.main";

    /**
     * SongSheetDetailComponent 对 SongSheetDetailActivity 的开放Api
     * (@link com.song.tasty.module.home.components.SongSheetDetailComponent for SongSheetDetailActivity )
     */
    String HOME_COMP_SONGSHEETDETAIL = "module.home.component.songsheetdetail";

    /**
     * MusicPlayComponent 对 MusicPlayActivity 的开放Api
     * (@link com.song.tasty.module.home.components.MusicPlayComponent for MusicPlayActivity )
     */
    String HOME_COMP_MUSICPLAY = "module.home.component.musicplay";
    //---------------------------------------------首页模块相关开放api------------------------------------------------------


    //---------------------------------------------云村模块相关开放api------------------------------------------------------
    //---------------------------------------------云村模块相关开放api------------------------------------------------------


    //---------------------------------------------音视模块相关开放api------------------------------------------------------

    /**
     * VideoComponent 对VideoFragment的开放Api
     * (@link com.song.tasty.module.video.components.VideoComponent for VideoFragment )
     */
    String VIDEO_COMP_MAIN = "module.video.component.main";
    //---------------------------------------------音视模块相关开放api------------------------------------------------------


    //---------------------------------------------我的模块相关开放api------------------------------------------------------
    /**
     * MineComponent 对MineFragment的开放Api
     * (@link com.song.tasty.module.mine.components.MineComponent for MineFragment )
     */
    String MINE_COMP_MAIN = "module.mine.components.main";


    //---------------------------------------------我的模块相关开放api------------------------------------------------------




    String  APP_COMP_FLUTTER = "app.comp.flutter";

}
