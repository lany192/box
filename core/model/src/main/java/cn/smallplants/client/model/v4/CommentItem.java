package cn.smallplants.client.model.v4;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.response.ReplyItem;
import cn.smallplants.client.model.response.UserItem;


//评论信息
public class CommentItem implements Serializable {
    //评论id
    @SerializedName("id")
    private long id;
    //创建时间
    @SerializedName("createTime")
    private long createTime;
    //评论的内容
    @SerializedName("content")
    private String content;
    //点赞数
    @SerializedName("likeCount")
    private long likeCount;
    //是否点赞
    @SerializedName("like")
    private boolean like;
    //作者
    @SerializedName("author")
    private UserItem author;
    //是否植物作者
    @SerializedName("plantAuthor")
    private boolean plantAuthor;
    //图片列表
    @SerializedName("images")
    private List<ImageItem> images;
    //回复列表，只取前面3条记录，更多评论去评论详情页查看
    @SerializedName("replies")
    private List<ReplyItem> replies;
    //植物id
    @SerializedName("plantId")
    private long plantId;
    //回复数量
    @SerializedName("replyCount")
    private long replyCount;
    //是否被置顶
    @SerializedName("top")
    private boolean top;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public boolean isPlantAuthor() {
        return plantAuthor;
    }

    public void setPlantAuthor(boolean plantAuthor) {
        this.plantAuthor = plantAuthor;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }

    public List<ReplyItem> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyItem> replies) {
        this.replies = replies;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
