package cn.smallplants.client.model.response;


import java.io.Serializable;

//点赞/取消点赞 结果
public class LikeResult implements Serializable {
    //是否被点赞
    private boolean like;
    //目标id
    private long id;

    public LikeResult(boolean like, long id) {
        this.like = like;
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
