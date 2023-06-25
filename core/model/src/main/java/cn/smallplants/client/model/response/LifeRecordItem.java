package cn.smallplants.client.model.response;


import java.io.Serializable;

import cn.smallplants.client.model.v4.ImageItem;

//晒图信息
public class LifeRecordItem implements Serializable {
    //封面图片
    ImageItem cover;
    //作者
    UserItem author;
    //晒图id
    private long lifeRecordId;
    //创建时间
    private long createTime;
    //标题
    private String title;
    //是否精华
    private boolean essence;
    //是否推荐
    private boolean recommend;
    //描述
    private String content;
    //浏览量
    private int views;
    //点赞量
    private long likeCount;
    //是否点赞
    private boolean like;

    public long getLifeRecordId() {
        return lifeRecordId;
    }

    public long getCreateTime() {
        return createTime;
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

    public String getContent() {
        return content;
    }

    public int getViews() {
        return views;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public UserItem getAuthor() {
        return author;
    }

    public boolean isLike() {
        return like;
    }
}
