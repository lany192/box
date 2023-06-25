package cn.smallplants.client.model.response;


import java.io.Serializable;

/**
 * @author lany
 */

//关注用户结果
public class AttentionResult implements Serializable {
    //请求者id
    private long uid;
    //目标用户id
    private long authorId;
    //关注状态，0未关注 1仅自己关注了对方 2仅对方关注了自己 3相互关注
    private int status;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
