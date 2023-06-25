package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.UserItem;

public class MettleItem {
    //晒图id
    @SerializedName("lifeRecordId")
    Long lifeRecordId;
    //创建时间
    @SerializedName("createTime")
    long createTime;
    //标题
    @SerializedName("title")
    String title;
    //封面图片
    @SerializedName("cover")
    ImageItem cover;
    //是否精华
    @SerializedName("essence")
    boolean essence;
    //是否推荐
    @SerializedName("recommend")
    boolean recommend;
    //描述
    @SerializedName("content")
    String content;
    //浏览量
    @SerializedName("views")
    long views;
    //点赞量
    @SerializedName("likeCount")
    long likeCount;
    //作者
    @SerializedName("author")
    UserItem author;
    //是否点赞
    @SerializedName("like")
    boolean like;

    public Long getLifeRecordId() {
        return lifeRecordId;
    }

    public void setLifeRecordId(Long lifeRecordId) {
        this.lifeRecordId = lifeRecordId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageItem getCover() {
        return cover;
    }

    public void setCover(ImageItem cover) {
        this.cover = cover;
    }

    public boolean isEssence() {
        return essence;
    }

    public void setEssence(boolean essence) {
        this.essence = essence;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
