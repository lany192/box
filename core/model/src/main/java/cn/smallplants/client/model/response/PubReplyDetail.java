package cn.smallplants.client.model.response;

//回复详情
public class PubReplyDetail extends ReplyItem {
    //评论总数
    private long commentCount;

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

}
