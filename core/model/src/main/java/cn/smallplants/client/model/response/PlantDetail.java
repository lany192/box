package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;


//植物详情信息
public class PlantDetail implements Serializable {
    //作者
    UserItem author;
    //封面
    ImageItem cover;
    //分享信息
    Share share;
    //植物id
    private long plantId;
    //创建时间
    private long createTime;
    //标题
    private String title;
    //成长记简介
    private String description;
    //是否精华
    private boolean essence;
    //是否推荐
    private boolean recommend;
    //浏览量
    private int views;
    //位置
    private String location;
    //日记数量
    private long diaryCount;
    //评论总数，包括子评论数量
    private long commentCount;
    //是否喜欢
    private boolean like;
    //点赞量
    private long likeCount;
    //专属二维码链接
    private String qrUrl;
    //淘口令商品
    private List<GoodsItem> goods;
    //植记
    private List<PlantItem> plants;

    public long getPlantId() {
        return plantId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEssence() {
        return essence;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public int getViews() {
        return views;
    }

    public String getLocation() {
        return location;
    }

    public UserItem getAuthor() {
        return author;
    }

    public ImageItem getCover() {
        return cover;
    }

    public long getDiaryCount() {
        return diaryCount;
    }

    public Share getShare() {
        return share;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public boolean isLike() {
        return like;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public List<PlantItem> getPlants() {
        return plants;
    }
}
