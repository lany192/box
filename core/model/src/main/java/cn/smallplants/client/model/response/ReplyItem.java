package cn.smallplants.client.model.response;


import java.io.Serializable;

//评论回复信息
public class ReplyItem implements Serializable {
    //发布者
    UserItem author;
    //被回复人
    UserItem toReplyAuthor;
    //所属评论id
    private long commentId;
    //当前回复id
    private long replyId;
    //回复内容
    private String content;
    //回复时间
    private long createTime;
    //点赞数量
    private long likeCount;
    //是否点赞
    private boolean like;
    //被回复的回复信息------------
    //被回复的内容是否被删除
    //被回复内容是否删除
    private boolean toReplyDelete;
    //被回复的回复id
    private long toReplyId;
    //被回复内容
    private String toReplyContent;
    //是否植物作者
    private boolean plantAuthor;

    //是否被置顶
    private boolean top;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserItem getAuthor() {
        return author;
    }

    public void setAuthor(UserItem author) {
        this.author = author;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public boolean isToReplyDelete() {
        return toReplyDelete;
    }

    public void setToReplyDelete(boolean toReplyDelete) {
        this.toReplyDelete = toReplyDelete;
    }

    public long getToReplyId() {
        return toReplyId;
    }

    public void setToReplyId(long toReplyId) {
        this.toReplyId = toReplyId;
    }

    public UserItem getToReplyAuthor() {
        return toReplyAuthor;
    }

    public void setToReplyAuthor(UserItem toReplyAuthor) {
        this.toReplyAuthor = toReplyAuthor;
    }

    public String getToReplyContent() {
        return toReplyContent;
    }

    public void setToReplyContent(String toReplyContent) {
        this.toReplyContent = toReplyContent;
    }

    public boolean isPlantAuthor() {
        return plantAuthor;
    }

    public void setPlantAuthor(boolean plantAuthor) {
        this.plantAuthor = plantAuthor;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
