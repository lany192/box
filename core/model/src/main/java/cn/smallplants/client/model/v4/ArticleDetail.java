package cn.smallplants.client.model.v4;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.smallplants.client.model.response.GoodsItem;
import cn.smallplants.client.model.response.UserItem;

public class ArticleDetail {
    //长文id
    @SerializedName("longTextId")
    private long articleId;

    //创建时间
    @SerializedName("createTime")
    private long createTime;

    //创建者ID
    @SerializedName("builderId")
    private long builderId;

    //拥有者ID
    @SerializedName("ownerId")
    private long ownerId;

    //标题
    @SerializedName("title")
    private String title;

    //封面图片
    @SerializedName("cover")
    private ImageItem cover;

    //是否精华
    @SerializedName("essence")
    private boolean essence;

    //是否推荐
    @SerializedName("recommend")
    private boolean recommend;

    //浏览量
    @SerializedName("views")
    private long views;

    //位置
    @SerializedName("location")
    private String location;

    //长文内容，存储html内容
    @SerializedName("content")
    private String content;

    //点赞量
    @SerializedName("likeCount")
    private long likeCount;

    //是否点赞
    @SerializedName("isLike")
    private boolean isLike;

    //作者
    @SerializedName("author")
    private UserItem author;

    //评论总数，包括子评论数量
    @SerializedName("commentCount")
    private long commentCount;

    //淘口令商品
    @SerializedName("goods")
    private List<GoodsItem> goods;

    //植记
    @SerializedName("plants")
    private List<PlantItem> plants;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getBuilderId() {
        return builderId;
    }

    public void setBuilderId(long builderId) {
        this.builderId = builderId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public List<GoodsItem> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsItem> goods) {
        this.goods = goods;
    }

    public List<PlantItem> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantItem> plants) {
        this.plants = plants;
    }
}
