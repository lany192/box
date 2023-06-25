package cn.smallplants.client.model.response;

import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.entity.Place;

/**
 * @author lany
 */

//个人主页用户信息
public class HomeDetail implements Serializable {
    //所在地区
    Place place;
    //用户等级
    Level level;
    //当前主页所有者用户id
    private long uid;
    //头像
    private String avatar;
    //昵称
    private String nickname;
    //性别
    private int sex;
    //年龄，如果为0，表示用户没有填写年龄
    private int age;
    //签名
    private String signature;
    //关注状态
    private int status;
    //粉丝数量
    private long fansCount;
    //关注的人数量
    private long followCount;
    //总点赞数
    private long likeCount;
    //是否打赏过这个用户
    private boolean reward;
    //用户发布植物数量
    private long publishPlantCount;
    //用户喜欢的植物数量
    private long likePlantCount;
    //用户发布长文数量
    private long longTextCount;
    //用户发布晒图数量
    private long lifeRecordCount;
    //是否VIP用户
    private boolean vip;

    //用户标签
    private List<String> labels;

    public boolean attention() {
        return status == 1 || status == 3;
    }

    public long getUid() {
        return uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getSignature() {
        return signature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getFansCount() {
        return fansCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public boolean isReward() {
        return reward;
    }

    public Place getPlace() {
        return place;
    }

    public Level getLevel() {
        return level;
    }

    public long getPublishPlantCount() {
        return publishPlantCount;
    }

    public long getLikePlantCount() {
        return likePlantCount;
    }

    public long getLongTextCount() {
        return longTextCount;
    }

    public long getLifeRecordCount() {
        return lifeRecordCount;
    }

    public boolean isVip() {
        return vip;
    }

    public List<String> getLabels() {
        return labels;
    }
}
