package com.song.tasty.module.home;

import com.song.tasty.module.home.entity.SongBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lichen
 * @date ：2019-07-24 20:32
 * @email : 196003945@qq.com
 * @description :
 */
public interface Constants {

    String HOME_HOST_ONLINE_URL = "http://appnew.bailemi.com/";
    String HOME_DOMAIN_NAME = "home";




    public static List<SongBean> getVideoList() {
        List<SongBean> songList = new ArrayList<>();
        songList.add(new SongBean("大家好，我是潇湘剑雨",
                "https://img-blog.csdnimg.cn/20201012215233584.png",
                "http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4"));

        songList.add(new SongBean("如果项目可以，可以给个star",
                "https://img-blog.csdnimg.cn/20201013092150588.png",
                "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"));

        songList.add(new SongBean("把本地项目代码复制到拷贝的仓库",
                "https://img-blog.csdnimg.cn/2020101309293329.png",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4"));

        songList.add(new SongBean("有bug，可以直接提出来，欢迎一起探讨",
                "https://img-blog.csdnimg.cn/20201013094115174.png",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4"));

        songList.add(new SongBean("逗比逗比把本地项目代码复制到拷贝的仓库",
                "https://img-blog.csdnimg.cn/20201013091432693.jpg",
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4"));

        songList.add(new SongBean("预告片6",
                "https://img-blog.csdnimg.cn/20201013091432695.jpg",
                "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4"));

        songList.add(new SongBean("预告片7",
                "https://img-blog.csdnimg.cn/20201013091432667.jpg",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4"));

        songList.add(new SongBean("大家好，我是潇湘剑雨逗比",
                "https://img-blog.csdnimg.cn/20201012215233584.png",
                "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4"));

        songList.add(new SongBean("依次输入命令上传代码",
                "https://img-blog.csdnimg.cn/20201013091432667.jpg",
                "http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4"));

        songList.add(new SongBean("依次输入命令上传代码",
                "https://img-blog.csdnimg.cn/20201013091432625.jpg",
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4"));

        songList.add(new SongBean("预告片11",
                "https://img-blog.csdnimg.cn/20201013091432602.jpg",
                "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4"));

        songList.add(new SongBean("依次输入命令上传代码",
                "https://img-blog.csdnimg.cn/20201013091432603.jpg",
                "http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4"));

        songList.add(new SongBean("如果项目可以，可以给个star",
                "https://img-blog.csdnimg.cn/20201013091432616.jpg",
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4"));

        songList.add(new SongBean("预告片14",
                "https://img-blog.csdnimg.cn/20201013091432581.jpg",
                "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4"));

        return songList;
    }

}
