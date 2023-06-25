package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;


//评论信息详情
public class PubCommentDetail implements Serializable {
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
    //回复数
    private long replyCount;
    //是否点赞
    private boolean like;
    //图片列表
    private List<ImageItem> images;
    //评论总数
    private long commentCount;

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

    public long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
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

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
