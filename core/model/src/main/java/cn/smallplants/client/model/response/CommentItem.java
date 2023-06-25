package cn.smallplants.client.model.response;


import java.io.Serializable;
import java.util.List;

import cn.smallplants.client.model.v4.ImageItem;


//评论信息
public class CommentItem implements Serializable {
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
    //是否点赞
    private boolean like;
    //是否植物作者
    private boolean plantAuthor;
    //图片列表
    private List<ImageItem> images;
    //回复列表，只取前面3条记录，更多评论去评论详情页查看
    private List<ReplyItem> replies;
    //植物id
    private long plantId;
    //回复数量
    private long replyCount;
    //是否被置顶
    private boolean top;

    public long getId() {
        return id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public boolean isLike() {
        return like;
    }

    public UserItem getAuthor() {
        return author;
    }

    public boolean isPlantAuthor() {
        return plantAuthor;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public List<ReplyItem> getReplies() {
        return replies;
    }

    public long getPlantId() {
        return plantId;
    }

    public long getReplyCount() {
        return replyCount;
    }

    public boolean isTop() {
        return top;
    }
}
