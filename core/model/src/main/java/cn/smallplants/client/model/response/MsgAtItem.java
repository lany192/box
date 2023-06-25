package cn.smallplants.client.model.response;


import java.io.Serializable;

//@我消息
public class MsgAtItem implements Serializable {
    //发送者
    UserItem sender;
    //消息id
    private long id;
    //创建时间
    private long createTime;
    //内容
    private String content;
    //是否已读
    private boolean read;
    //评论ID
    private long commentId;
    //回复id
    private long replyId;
    //是否已经删除
    private boolean deleted;//TODO

    public long getId() {
        return id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return read;
    }

    public long getCommentId() {
        return commentId;
    }

    public long getReplyId() {
        return replyId;
    }

    public UserItem getSender() {
        return sender;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
