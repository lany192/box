package cn.smallplants.client.model.response;


import java.io.Serializable;

//点赞消息
public class MsgLikeItem implements Serializable {
    //发送者
    UserItem sender;
    //点赞消息id
    private long id;
    //点赞类型，1点赞植物，2点赞评论，3点赞回复，4点赞长文，5点赞晒图
    private long type;
    //创建时间
    private long createTime;
    //是否已读
    private boolean read;
    //植物id，老版本用这个字段
    private long plantId;
    //关联ID（植记，晒图，长文）V2新版本拿这个字段
    private long relationId;
    //评论id
    private long commentId;
    //回复id
    private long replyId;
    //封面图片
    private String cover;
    //内容
    private String content;

    //是否已经删除
    private boolean deleted;//TODO

    public long getId() {
        return id;
    }

    public long getType() {
        return type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean isRead() {
        return read;
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

    public String getCover() {
        return cover;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
