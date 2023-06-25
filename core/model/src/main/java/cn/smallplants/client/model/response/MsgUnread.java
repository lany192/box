package cn.smallplants.client.model.response;


import java.io.Serializable;

/**
 * @author lany
 */

//未读消息信息
public class MsgUnread implements Serializable {
    //系统消息未读数量
    private long systemUnreadCount;
    //系统消息未读描述
    private String systemMessage;

    //关注消息未读数量
    private long attentionUnreadCount;
    //关注消息未读描述
    private String attentionMessage;

    //互动消息未读数量
    private long mixtureUnreadCount;
    //互动消息未读描述
    private String mixtureMessage;

    public long getTotalCount() {
        return systemUnreadCount + attentionUnreadCount + mixtureUnreadCount;
    }

    public long getSystemUnreadCount() {
        return systemUnreadCount;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public long getAttentionUnreadCount() {
        return attentionUnreadCount;
    }

    public String getAttentionMessage() {
        return attentionMessage;
    }

    public long getMixtureUnreadCount() {
        return mixtureUnreadCount;
    }

    public String getMixtureMessage() {
        return mixtureMessage;
    }
}
