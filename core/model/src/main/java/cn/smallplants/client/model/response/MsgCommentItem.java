package cn.smallplants.client.model.response;


import java.io.Serializable;

//评论/回复 消息
public class MsgCommentItem implements Serializable {
    //发送者
    UserItem sender;
    //消息id
    private long id;
    //创建时间
    private long createTime;
    //消息描述
    private String content;
    //封面图片
    private String cover;
    //是否已读
    private boolean read;
    //是否是回复消息
    private boolean reply;
    //植物id
    private long plantId;
    //关联ID（植记，晒图，长文）V2新版本拿这个字段
    private long relationId;
    //评论id
    private long commentId;
    //被回复ID
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

    public String getCover() {
        return cover;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isReply() {
        return reply;
    }

    public long getPlantId() {
        return plantId;
    }

    public long getRelationId() {
        return relationId;
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