package cn.smallplants.client.model.response;


import java.io.Serializable;

import cn.smallplants.client.model.v4.ImageItem;

//植物信息
public class PlantItem implements Serializable {
    //功能类型：0：植物，1：长文，2：晒图
    Integer functionType;
    //作者
    UserItem author;
    //封面
    ImageItem cover;
    //植物id
    private long plantId;
    //创建时间
    private long createTime;
    //标题
    private String title;
    //    //成长记简介
//private    String description;
    //是否精华
    private boolean essence;
    //是否推荐
    private boolean recommend;
    //点赞量
    private long likeCount;
    //游览量
    private long views;
    //是否点赞
    private boolean like;
//    //专属二维码链接
//private    String qrUrl;

    public long getPlantId() {
        return plantId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEssence() {
        return essence;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getViews() {
        return views;
    }

    public Integer getFunctionType() {
        return functionType;
    }

    public UserItem getAuthor() {
        return author;
    }

    public ImageItem getCover() {
        return cover;
    }

    public boolean isLike() {
        return like;
    }
}
