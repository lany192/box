package cn.smallplants.client.model.response;


import java.io.Serializable;

import cn.smallplants.client.model.entity.Place;

//用户信息
public class UserItem implements Serializable {
    //用户id
    private long uid;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //性别0保密1男2女
    private int sex;
    //关注状态,0未关注 1仅自己关注了对方 2仅对方关注了自己 3相互关注
    private int status;
    //是否VIP
    private boolean vip;
    //用户等级
    private Level level;
    //是否官方
    private boolean official;
    //所属地
    private Place place;
    //生日年月日
    private String birthday;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean attention() {
        return status == 1 || status == 3;
    }
}
