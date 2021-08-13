package com.song.tasty.module.home.entity;

/**
 * @author lichen
 * @date ：2019-09-02 22:58
 * @email : 196003945@qq.com
 * @description :
 */
public class SongBean {


    /**
     * songid : 4226
     * delsongid : 4226
     * name : Alex Hepburn - Under
     * playurl : https://x128.bailemi.com/xrkjpay/music/202004/24/5912078.mp3
     * downurl : https://x128.bailemi.com/xrkjpay/music/202004/24/5912078.mp3
     * uid : 10
     * up_user : 高圆圆不是我
     * up_user_logo : https://x128.bailemi.com/attachment/20191224/mgnY5HdbitPLkrMOlFTG.jpg
     * down_con : 5
     * singerid : 27323
     * usergood : 0
     * userpl : 0
     * userdown : 0
     * userfav : 0
     * mpicx : https://x128.bailemi.com/xrkjpay/music/202004/24/2053220.jpg
     * addtime : 43分钟前
     * singer : Alex Hepburn
     */

    private String songid;
    private String delsongid;
    private String name;
    private String playurl;
    private String downurl;
    private String videourl;
    private String uid;
    private String up_user;
    private String up_user_logo;
    private String down_con;
    private String singerid;
    private String usergood;
    private String userpl;
    private String userdown;
    private String userfav;
    private String mpicx;
    private String addtime;
    private String singer;

    public SongBean() {
    }

    public SongBean(String name, String playurl, String videourl) {
        this.name = name;
        this.playurl = playurl;
        this.videourl = videourl;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getDelsongid() {
        return delsongid;
    }

    public void setDelsongid(String delsongid) {
        this.delsongid = delsongid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUp_user() {
        return up_user;
    }

    public void setUp_user(String up_user) {
        this.up_user = up_user;
    }

    public String getUp_user_logo() {
        return up_user_logo;
    }

    public void setUp_user_logo(String up_user_logo) {
        this.up_user_logo = up_user_logo;
    }

    public String getDown_con() {
        return down_con;
    }

    public void setDown_con(String down_con) {
        this.down_con = down_con;
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid;
    }

    public String getUsergood() {
        return usergood;
    }

    public void setUsergood(String usergood) {
        this.usergood = usergood;
    }

    public String getUserpl() {
        return userpl;
    }

    public void setUserpl(String userpl) {
        this.userpl = userpl;
    }

    public String getUserdown() {
        return userdown;
    }

    public void setUserdown(String userdown) {
        this.userdown = userdown;
    }

    public String getUserfav() {
        return userfav;
    }

    public void setUserfav(String userfav) {
        this.userfav = userfav;
    }

    public String getMpicx() {
        return mpicx;
    }

    public void setMpicx(String mpicx) {
        this.mpicx = mpicx;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
