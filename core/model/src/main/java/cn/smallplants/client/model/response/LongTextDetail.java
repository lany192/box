package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;

//长文详情信息
public class LongTextDetail implements Serializable {
    //封面图片
    ImageItem cover;
    //作者
    UserItem author;
    //长文id
    private long longTextId;
    //创建时间
    private long createTime;
    //创建者ID
    private long builderId;
    //拥有者ID
    private long ownerId;
    //标题
    private String title;
    //是否精华
    private boolean essence;
    //是否推荐
    private boolean recommend;
    //浏览量
    private int views;
    //位置
    private String location;
    //长文内容，存储html内容
    private String content;
    //点赞量
    private long likeCount;
    //是否点赞
    private boolean isLike;
    //评论总数，包括子评论数量
    private long commentCount;
    //淘口令商品
    private List<GoodsItem> goods;
    //植记
    private List<PlantItem> plants;

    public long getLongTextId() {
        return longTextId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getBuilderId() {
        return builderId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public ImageItem getCover() {
        return cover;
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

    public String getContent() {
        return content;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public UserItem getAuthor() {
        return author;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public List<PlantItem> getPlants() {
        return plants;
    }
}
