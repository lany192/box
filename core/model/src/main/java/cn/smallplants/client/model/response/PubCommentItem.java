package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;


//评论信息
public class PubCommentItem implements Serializable {
    //作者
    UserItem author;
    //评论id
    private long id;
    //创建时间
    private long createTime;
    //评论的内容
    private String content;
    //点赞数
    private long likeCount;
    //是否点赞
    private boolean like;
    //是否作者
    private boolean isArticleAuthor;
    //图片列表
    private List<ImageItem> images;
    //回复列表，只取前面3条记录，更多评论去评论详情页查看
    private List<PubReplyItem> replies;
    //关联id
    private long relationId;
    //回复数量
    private long replyCount;
    //是否被置顶
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

    public boolean isArticleAuthor() {
        return isArticleAuthor;
    }

    public void setArticleAuthor(boolean articleAuthor) {
        isArticleAuthor = articleAuthor;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }

    public List<PubReplyItem> getReplies() {
        return replies;
    }

    public void setReplies(List<PubReplyItem> replies) {
        this.replies = replies;
    }

    public long getRelationId() {
        return relationId;
    }

    public void setRelationId(long relationId) {
        this.relationId = relationId;
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
