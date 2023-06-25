package cn.smallplants.client.model.response;


import java.io.Serializable;

//关注消息
public class MsgAttentionItem implements Serializable {
    //发送者
    UserItem author;
    //消息id
    private long id;
    //创建时间
    private long createTime;
    //内容
    private String message;
    //是否已读
    private boolean read;

    public long getId() {
        return id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public UserItem getAuthor() {
        return author;
    }
}
