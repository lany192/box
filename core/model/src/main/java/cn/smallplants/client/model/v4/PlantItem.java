package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import cn.smallplants.client.model.response.UserItem;

public class PlantItem {
    //植物id
    @SerializedName("plantId")
    long plantId;
    //创建时间
    @SerializedName("createTime")
    long createTime;
    //标题
    @SerializedName("title")
    String title;
    //是否精华
    @SerializedName("essence")
    boolean essence;
    //是否推荐
    @SerializedName("recommend")
    boolean recommend;
    //点赞量
    @SerializedName("likeCount")
    long likeCount;
    //游览量
    @SerializedName("views")
    long views;
    //功能类型：0：植物，1：长文，2：晒图
    @SerializedName("functionType")
    int type;
    //作者
    @SerializedName("author")
    UserItem author;
    //封面
    @SerializedName("cover")
    ImageItem cover;
    //是否点赞
    @SerializedName("like")
    boolean like;

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
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

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public ImageItem getCover() {
        return cover;
    }

    public void setCover(ImageItem cover) {
        this.cover = cover;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
